package MarketProject.backend.service;

import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Seller;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(CustomerDto customerDto);

    String updateCustomer(CustomerDto customerDto);  // !!!can specify

    Long chooseProduct();

    Comment makeComment(String expression);

    List<CommentDto> getComments(Long customer_id);

    String getNotification(Long product_id); //can be list

    void makeFeedback();





}
