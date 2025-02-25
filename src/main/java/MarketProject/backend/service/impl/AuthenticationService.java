package MarketProject.backend.service.impl;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.UnmatchedPersonException;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.dto.responseDto.UserRequest;
import MarketProject.backend.dto.responseDto.UserResponse;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Seller;
import MarketProject.backend.entity.enums.Role;
import MarketProject.backend.repository.CustomerRepository;
import MarketProject.backend.repository.PersonRepository;
import MarketProject.backend.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    private final PersonRepository personRepository;


    @Transactional
    public UserResponse saveSeller(SellerDto sellerDto) throws AlreadyRegisteredUsernameException {

        Seller seller;
        String username = sellerDto.getUsername();
        seller = sellerRepository.findSellerByUsername(username);
        if (seller != null) {

            throw new AlreadyRegisteredUsernameException();

        }

        Seller seller1 = new Seller(); //seller throws null exception
        seller1.setName(sellerDto.getName());
        seller1.setSurname((sellerDto.getSurname()));
        seller1.setUsername(sellerDto.getUsername());
        seller1.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        seller1.setAge(sellerDto.getAge());
        seller1.setJoined_at(new Date());
        seller1.setRole(Role.ROLE_SELLER);

        sellerRepository.save(seller1);
        System.out.println("Seller " + sellerDto.getName() + " added successfully ");

        var token = jwtService.generateToken(seller1);

        return UserResponse.builder().token((String) token).build(); //casting

    }

    @Transactional
    public UserResponse saveCustomer(CustomerDto customerDto) throws AlreadyRegisteredUsernameException {

        Customer customer;
        String username = customerDto.getUsername();
        customer = customerRepository.findCustomerByUsername(username);
        if (customer != null) {

            throw new AlreadyRegisteredUsernameException();

        }

        Customer customer1 = new Customer();   // customer throws null excp.

        customer1.setName(customerDto.getName());
        customer1.setSurname(customerDto.getSurname());
        customer1.setUsername(customerDto.getUsername());
        customer1.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer1.setAge(customerDto.getAge());
        customer1.setJoined_at(new Date());
        //customer1.setBalance(customerDto.getBalance());
        customer1.setRole(Role.ROLE_CUSTOMER);

        customerRepository.save(customer1);
        System.out.println("Customer " + customerDto.getName() + " added successfully");

        var token = jwtService.generateToken(customer1);

        return UserResponse.builder().token((String) token).build();
    }


    public UserResponse authSeller(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Seller seller = sellerRepository.findByUsername(userRequest.getUsername()).orElseThrow(); // ****
        String token = (String) jwtService.generateToken(seller);

        return UserResponse.builder().token(token).build();
    }

    public UserResponse authCustomer(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Customer customer = customerRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = (String) jwtService.generateToken(customer);

        return UserResponse.builder().token(token).build();

    }

    public Customer getCustomDetails(String token, String username) throws UnmatchedPersonException {

        if (jwtService.checkTokenUsername(token,username)) {

            return customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        }

        throw new UnmatchedPersonException();
    }
}

