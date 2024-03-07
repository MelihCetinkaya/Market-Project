package MarketProject.backend.service.impl;

import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private  final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    @Override
    @Transactional
    public Customer saveCustomer(CustomerDto customerDto) {

        Customer customer =new Customer();
        customer.setCustomer_name(customerDto.getCustomer_name());
        customer.setCustomer_surname(customerDto.getCustomer_surname());
        customer.setCustomer_age(customerDto.getCustomer_age());
        customer.setComments(null);
        customer.setJoined_at(new Date());

        customerRepository.save(customer);
        System.out.println("Customer "+customerDto.getCustomer_name()+" added successfully");

        return customer; //could change
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
        Product product=productRepository.findById(productId).orElse(null);// could be optional
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
    public Notification createNotification(Long product_id,Date date) {

        Notification notification=new Notification();

        notification.setNotification_date(date);
        notification.setNotificationType(NotificationType.timer);
        notification.setNotificationRelation(NotificationRelation.product);
        notification.setProduct(productRepository.findById(product_id).orElse(null));
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
}
