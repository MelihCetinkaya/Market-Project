package MarketProject.backend.service.impl;

import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.NotificationDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;
import MarketProject.backend.entity.enums.CommentType;
import MarketProject.backend.repository.CommentRepository;
import MarketProject.backend.repository.NotificationRepository;
import MarketProject.backend.repository.ProductRepository;
import MarketProject.backend.repository.SellerRepository;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;

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
        return productDto;

    }

    @Override
    @Transactional
    public Seller saveSeller(SellerDto sellerDto) {
        Seller seller = new Seller();
        seller.setName(sellerDto.getName());
        seller.setSurname((sellerDto.getSurname()));
        seller.setAge(sellerDto.getAge());
        seller.setJoined_at(new Date());

        sellerRepository.save(seller);
        System.out.println("User "+sellerDto.getName()+" added successfully ");

        return seller;
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

    @Override
    public Product getProduct(Long productId) {  // could be productDto
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<CommentDto> getMarketComments() {

        List<Comment>comments=commentRepository.findByCommentType(CommentType.market);

        List<CommentDto>commentDtos=new ArrayList<>();

        comments.forEach(comment -> {

            CommentDto commentDto=new CommentDto();

            commentDto.setCommentId(comment.getCommentId());
            commentDto.setComment_expression(comment.getComment_expression());
            commentDto.setCommentType(CommentType.market);
            commentDto.setProduct(null); // ******
            commentDto.setAdded_at(new Date());
            commentDto.setUpdated_at(null);

            commentDtos.add(commentDto);


        });

        return commentDtos;
    }

    @Override
    public List<NotificationDto> getMarketNotifications() {

        List<Notification> notifications= notificationRepository.findAll();

        List<NotificationDto>notificationDtos=new ArrayList<>();

        notifications.forEach(notification ->{

            NotificationDto notificationDto=new NotificationDto();

            notificationDto.setNotification_message(notification.getNotification_message());
            notificationDto.setNotification_id(notification.getNotification_id());
            notificationDto.setNotification_date(notification.getNotification_date());
            notificationDto.setNotificationType(notification.getNotificationType());
            notificationDto.setNotificationRelation(notification.getNotificationRelation());//*****
            notificationDto.setProduct(null);
            notificationDto.setCreated_at(new Date());
            notificationDtos.add(notificationDto);


        });

        return notificationDtos;
    }


}
