package MarketProject.backend.service;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.PersonNotFoundException;
import MarketProject.backend.dto.*;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Seller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;
import java.util.List;

public interface CustomerService {

    String updateCustomer(CustomerDto customerDto);  // !!!can specify

    Long chooseProduct();

    Comment makeComment(String expression);

    List<CommentDto> getComments(Long customer_id);

    SseEmitter createNotification(String token, String username, Long product_id); //can be list

    void dispatchEvents(Long product_id);

    void makeFeedback(String expression);

    List<ProductDto> getProducts();

    List<MarketDto> getMarkets();
}
