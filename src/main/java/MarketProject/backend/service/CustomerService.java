package MarketProject.backend.service;

import MarketProject.backend.api.exceptionApi.exceptions.PersonNotFoundException;
import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Seller;

import java.util.Date;
import java.util.List;

public interface CustomerService {

    CustomerDto login(String username,String password) throws PersonNotFoundException;

    Customer saveCustomer(CustomerDto customerDto);

    String updateCustomer(CustomerDto customerDto);  // !!!can specify

    Long chooseProduct();

    Comment makeComment(String expression);

    List<CommentDto> getComments(Long customer_id);

    Notification createNotification(Long product_id, Date date); //can be list

    void makeFeedback(String expression);





}
