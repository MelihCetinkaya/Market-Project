package MarketProject.backend.service.impl;


import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.MarketDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.*;
import MarketProject.backend.entity.enums.CommentType;
import MarketProject.backend.repository.*;
import MarketProject.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final MarketRepository marketRepository;
    private final NotificationRepository notificationRepository;
    private final JwtService jwtService;

    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> productEmitters;


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

    //List<SseEmitter> emitters =new CopyOnWriteArrayList<>();
    //ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> productEmitters = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public SseEmitter createNotification(String token,String username,Long product_id) {

        if (jwtService.checkTokenUsername(token,username)) {

            SseEmitter sseEmitter=new SseEmitter(30_000L);

            productEmitters.computeIfAbsent(product_id, k -> new CopyOnWriteArrayList<>()).add(sseEmitter);

            try {
                sseEmitter.send(SseEmitter.event().name("INIT"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


           /* Notification notification=new Notification();

            notification.setNotification_date(new Date());
            notification.setNotificationType(NotificationType.timer);
            notification.setNotificationRelation(NotificationRelation.product);
            notification.setProduct(productRepository.findProductById(product_id));
            notification.setNotification_message("Notification created for product number " + product_id);
            notification.setCreated_at(new Date());


            notificationRepository.save(notification);*/
            System.out.println("abonelik eklendi");
            return sseEmitter;
        }


        return null;  //  throw new exception
    }

    public void dispatchEvents(Long product_id){

        CopyOnWriteArrayList<SseEmitter> emitters = productEmitters.get(product_id);

        for(SseEmitter emitter : emitters){

            try {
                emitter.send(SseEmitter.event().name("latest name"));
            } catch (IOException e) {
                emitters.remove(emitter);
            }

        }
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

    @Override
    public List<MarketDto> getMarkets() {

        List<Market>markets=marketRepository.getMarkets();
        List<MarketDto>marketDtos=new ArrayList<>();

        markets.forEach(market ->{

            MarketDto marketDto =new MarketDto();

            marketDto.setMarketName(market.getMarketName());
            marketDto.setMarketId(market.getMarketId());
            marketDto.setOpening_time(market.getOpening_time());
            marketDto.setClosing_time(market.getClosing_time());

            marketDtos.add(marketDto);
        });

        return marketDtos;
    }
}
