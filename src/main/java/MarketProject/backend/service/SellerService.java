package MarketProject.backend.service;

import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.NotificationDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

 SellerDto login(String username,String password);

 ProductDto addProduct(ProductDto productDto);

 Seller saveSeller(SellerDto sellerDto);

 String updateSeller(SellerDto sellerDto);  // !!!can specify

 List<ProductDto> getProducts();//share with customer

 Product getProduct(Long productId);//share with customer

 List<CommentDto> getMarketComments();

 List<NotificationDto> getMarketNotifications();

}
