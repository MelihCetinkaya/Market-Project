package MarketProject.backend.service.impl;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.PersonNotFoundException;
import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.enums.CommentType;
import MarketProject.backend.entity.enums.NotificationRelation;
import MarketProject.backend.entity.enums.NotificationType;
import MarketProject.backend.repository.CommentRepository;
import MarketProject.backend.repository.CustomerRepository;
import MarketProject.backend.repository.NotificationRepository;
import MarketProject.backend.repository.ProductRepository;
import MarketProject.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private  final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;



    @Override
    public CustomerDto login(String username, String password) throws PersonNotFoundException {

        Customer customer=customerRepository.findCustomerByUsernameAndPassword(username,password);

        if (customer == null) {
            throw new PersonNotFoundException();
        }

        CustomerDto customerDto=new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAge(customer.getAge());
        customerDto.setJoined_at(customer.getJoined_at());

        return customerDto;

    }
    public Customer getCustomerByUsername(String username){

        return customerRepository.findCustomerByUsername(username);
        //return customerRepository.findByUsername(username);
    }
    @Override
    @Transactional
    public Customer saveCustomer(CustomerDto customerDto) throws AlreadyRegisteredUsernameException {

        Customer customer;
        String username=customerDto.getUsername();
        customer=customerRepository.findCustomerByUsername(username);
 if(customer!=null) {

     throw new AlreadyRegisteredUsernameException();

 }

 Customer customer1 =new Customer();   // customer throws null excp.

        customer1.setName(customerDto.getName());
        customer1.setSurname(customerDto.getSurname());
        customer1.setUsername(customerDto.getUsername());
        customer1.setPassword(customerDto.getPassword());
        customer1.setAge(customerDto.getAge());
        customer1.setJoined_at(new Date());
        // customer1.setAccountNonExpired(true);
        //customer1.setAccountNonLocked(false);

        customer1.setBalance(customerDto.getBalance());


        customerRepository.save(customer1);
        System.out.println("Customer "+customerDto.getName()+" added successfully");

        return customer1; //could change
    }

    @Override
    public String updateCustomer(CustomerDto customerDto) {



        return null;
    }

    @Override
    public Long chooseProduct() {
        return 0L;
    }


    @Override
    public Comment makeComment(String expression) {

        Long productId=chooseProduct();//*****
        Product product=productRepository.findProductById(productId);// could be optional
        Comment comment=new Comment();
        comment.setComment_expression(expression);
        comment.setProduct(product);
        comment.setCommentType(CommentType.product); // *****
        comment.setAdded_at(new Date());
        comment.setUpdated_at(null);



   commentRepository.save(comment);

return comment;

    }

    @Override
    public List<CommentDto> getComments(Long product_id) {

        List<Comment>comments=commentRepository.findProductCommentsByProductId(product_id,CommentType.product);

        List<CommentDto>commentDtos=new ArrayList<>();

        comments.forEach(comment -> {

            CommentDto commentDto=new CommentDto();

            commentDto.setCommentId(comment.getCommentId());
            commentDto.setComment_expression(comment.getComment_expression());
            commentDto.setCommentType(CommentType.product);
            commentDto.setProduct(null); // ******
            commentDto.setAdded_at(new Date());
            commentDto.setUpdated_at(null);

            commentDtos.add(commentDto);


        });

        return commentDtos;
    }

    @Override
    @Transactional
    public Notification createNotification(Long product_id) {

        Notification notification=new Notification();

        notification.setNotification_date(new Date());
        notification.setNotificationType(NotificationType.timer);
        notification.setNotificationRelation(NotificationRelation.product);
        notification.setProduct(productRepository.findProductById(product_id));
        notification.setNotification_message("Notification created for product number " + product_id);
        notification.setCreated_at(new Date());


        notificationRepository.save(notification);

        return notification;
    }

    @Override
    public void makeFeedback(String expression) {

        Notification notification=new Notification();
        Comment comment=new Comment();

        comment.setComment_expression(expression);
        comment.setProduct(null);
        comment.setCommentType(CommentType.market);
        comment.setAdded_at(new Date());
        comment.setUpdated_at(null);

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
