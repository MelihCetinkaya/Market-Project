package MarketProject.backend.service;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.MarketNotFoundException;
import MarketProject.backend.dto.*;
import MarketProject.backend.entity.*;

import java.util.List;
import java.util.Optional;

public interface SellerService {

 ProductDto addProduct(ProductDto productDto,String marketName) throws MarketNotFoundException;

 String updateSeller(SellerDto sellerDto);  // !!!can specify

 Product getProduct(Long productId);//share with customer

 List<CommentDto> getMarketComments();

 List<NotificationDto> getMarketNotifications();

 MarketDto createMarket(String username,String marketName);


 Market chooseMArket(String marketName) throws MarketNotFoundException;


 List<ProductDto> getProductsOfMarket(String marketName);
}
