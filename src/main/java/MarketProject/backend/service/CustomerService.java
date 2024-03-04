package MarketProject.backend.service;

import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Seller;

import java.util.List;

public interface CustomerService {

    void saveCustomer(Customer customer);

    String updateCustomer(CustomerDto customerDto);  // !!!can specify

    void makeComment();

    List<Comment> getComments(int customer_id);

    String getNotification(int product_id); //can be list

    void makeFeedback();




}
