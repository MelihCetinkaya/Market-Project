package MarketProject.backend.service.impl;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.MarketNotFoundException;
import MarketProject.backend.dto.*;
import MarketProject.backend.dto.FuncDtos.MarketNameDto;
import MarketProject.backend.dto.FuncDtos.SellerProfileDto;
import MarketProject.backend.entity.*;
import MarketProject.backend.entity.enums.CommentType;
import MarketProject.backend.repository.*;
import MarketProject.backend.service.SellerService;
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
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final MarketRepository marketRepository;

    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> productEmitters;

    @Override
    @Transactional
    public void addProduct(ProductDto productDto,String marketName) throws MarketNotFoundException {

        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setStock_amount(productDto.getStock_amount());
        product.setStock_status(productDto.getStock_status());
        product.setPrice(productDto.getPrice());
        product.setMarket(marketRepository.findByMarketName(marketName).orElseThrow(MarketNotFoundException::new));
        product.setComments(null);
        product.setAdded_at(new Date());
        product.setSupplyDate(null);

        productRepository.save(product);
        System.out.println("Product added successfully");


        CopyOnWriteArrayList<SseEmitter> emitters = productEmitters.get(product.getProductId());

        if(emitters == null){

            System.out.println("emitter null");

        }

       else {
            for (SseEmitter emitter : emitters) {

                try {
                    emitter.send(SseEmitter.event().name("The product named " + productDto.getProductName() +
                            "was added to stock by seller "));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }

            }
        }


    }

    @Override
    public String updateSeller(SellerDto sellerDto) {
        return null;
    }



    @Override
    public Product getProduct(Long productId) {  // could be productDto
        return productRepository.findProductById(productId);
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

    @Override
    @Transactional
    public MarketDto createMarket(String username,String marketName) {
        Seller seller=sellerRepository.findSellerByUsername(username);
        Market newMarket=new Market();
        newMarket.setMarketName(marketName);
        newMarket.setSeller(seller);
        seller.getMarkets().add(newMarket);
        marketRepository.save(newMarket);

        MarketDto marketDto=new MarketDto();
        marketDto.setMarketName(marketName);
        marketDto.setOpening_time(new Date());
        return marketDto;
    }

    @Override
    public Market chooseMArket(String marketName) throws MarketNotFoundException {

        return marketRepository.findByMarketName(marketName).orElseThrow(MarketNotFoundException::new);
    }

    @Override
    public List<ProductDto> getProductsOfMarket(String marketName) {

        List<Product> products = productRepository.findSellerProductBYMarketName(marketName);

        List<ProductDto> productDtos = new ArrayList<>();

        products.forEach(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setStock_amount(product.getStock_amount());
            productDto.setStock_status(product.getStock_status());
            productDto.setPrice(product.getPrice());
            productDto.setComments(product.getComments().stream().map(Comment::getComment_expression)
                    .collect(Collectors.toList()));

            productDto.setAdded_at(product.getAdded_at()); //****
            productDto.setSupplyDate(product.getSupplyDate());
            productDtos.add(productDto);
        });

        return productDtos;
    }

    @Override
    public SellerProfileDto getMyProfile(String username) {

       Seller seller= sellerRepository.findSellerByUsername(username);
       SellerProfileDto sellerProfileDto = new SellerProfileDto();
       sellerProfileDto.setName(seller.getName());
       sellerProfileDto.setSurname(seller.getSurname());
       sellerProfileDto.setAge(seller.getAge());
       sellerProfileDto.setUsername(seller.getUsername());


        return sellerProfileDto;
    }

    @Override
    public List<MarketNameDto> getMyMarkets(String username) {

        Seller seller= sellerRepository.findSellerByUsername(username);
        List<String>marketNames=marketRepository.findSellerMarkets(seller.getUsername());
        List<MarketNameDto> marketNameDtos =new ArrayList<>();
        
        marketNames.forEach(marketName->{
            
             MarketNameDto marketNameDto =new MarketNameDto();
             marketNameDto.setMarketName(marketName);
             marketNameDtos.add(marketNameDto);
            
        });
        
        
        return marketNameDtos;
    }


}
