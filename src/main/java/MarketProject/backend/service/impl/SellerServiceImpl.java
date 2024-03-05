package MarketProject.backend.service.impl;

import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;
import MarketProject.backend.repository.ProductRepository;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;


    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {

Product product = new Product();
product.setProductName(productDto.getProductName());
product.setStock_amount(productDto.getStock_amount());
product.setStock_status(productDto.getStock_status());
product.setComments(null);
product.setAdded_at(new Date());
product.setSupplyDate(null);

productRepository.save(product);
        System.out.println("Product added successfully");
       return   productDto;

    }

    @Override
    public void saveSeller(Seller seller) {

    }

    @Override
    public String updateSeller(SellerDto sellerDto) {
        return null;
    }

    @Override
    public List<ProductDto> getProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();

        products.forEach(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setStock_amount(product.getStock_amount());
            productDto.setStock_status(product.getStock_status());
            productDto.setComments(product.getComments().stream().map(Comment::getComment_expression)
                    .collect(Collectors.toList()));

            productDto.setAdded_at(product.getAdded_at()); //****
            productDto.setSupplyDate(product.getSupplyDate());
           productDtos.add(productDto);
        });


        return productDtos;
    }
}
