package MarketProject.backend.service.impl;

import MarketProject.backend.api.exceptionApi.exceptions.AccessRestrictionException;
import MarketProject.backend.api.exceptionApi.exceptions.InsufficientBalanceException;
import MarketProject.backend.api.exceptionApi.exceptions.ProductNotFoundException;
import MarketProject.backend.configuration.JwtService;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Product;
import MarketProject.backend.repository.CustomerRepository;
import MarketProject.backend.repository.ProductRepository;
import MarketProject.backend.service.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomServiceImpl implements CustomService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    @Override
    @Transactional
    public String addToCart(String token,String username,Long productId) throws ProductNotFoundException, AccessRestrictionException { // could be id

        // name price number ilişkisi değişsitirilebilir(adet yok)

        if (jwtService.checkTokenUsername(token,username)) {
            Product product = productRepository.findProductById(productId);
            Customer customer = customerRepository.findCustomerByUsername(username);

            boolean checkStock = product.getStock_status();  // stock check

            if (checkStock) {

                product.setStock_amount(product.getStock_amount() - 1);
                boolean checkExist = customer.getAddedProducts().containsKey(product.getProductName());

                if (checkExist) {

                    int currentValue = customer.getAddedProducts().get(product.getProductName());
                    int newValue = currentValue + product.getPrice();
                    customer.getAddedProducts().put(product.getProductName(), newValue);


                } else {
                    customer.getAddedProducts().put(product.getProductName(), product.getPrice());

                }

                if (product.getStock_amount() == 0) {

                    product.setStock_status(false);
                }
                return product.getProductName() + " has been successfully added to your cart.";

            }

            throw new ProductNotFoundException();
        }

        throw new AccessRestrictionException();

    }

    @Transactional
    @Override
    public String confirmTransaction(String token,String username) throws InsufficientBalanceException, AccessRestrictionException {

        if (jwtService.checkTokenUsername(token, username)) {
            Customer customer = customerRepository.findCustomerByUsername(username);

            int sum = customer.getAddedProducts().values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            boolean checkBalance = customer.getBalance() >= sum;  // balance check

            if (!checkBalance) {

                throw new InsufficientBalanceException();
            }
            customer.getAddedProducts().clear();
            customer.setBalance(customer.getBalance() - sum);

            return "Your purchase of $" + sum + " has been completed.";
        }

        throw new AccessRestrictionException();
    }
}
