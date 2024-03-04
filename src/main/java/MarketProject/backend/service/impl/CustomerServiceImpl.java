package MarketProject.backend.service.impl;

import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public void saveCustomer(Customer customer) {

    }

    @Override
    public String updateCustomer(CustomerDto customerDto) {
        return null;
    }

    @Override
    public void makeComment() {

    }

    @Override
    public List<Comment> getComments(int customer_id) {
        return null;
    }

    @Override
    public String getNotification(int product_id) {
        return null;
    }

    @Override
    public void makeFeedback() {

    }
}
