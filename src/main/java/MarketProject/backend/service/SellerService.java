package MarketProject.backend.service;

import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;

import java.util.List;

public interface SellerService {

 void saveSeller(Seller seller);

 String updateSeller(SellerDto sellerDto);  // !!!can specify

 List<Product> getProducts(int seller_id);


}
