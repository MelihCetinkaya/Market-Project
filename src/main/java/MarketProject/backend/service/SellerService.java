package MarketProject.backend.service;

import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.dto.SellerDto;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;

import java.util.List;

public interface SellerService {

 ProductDto addProduct(ProductDto productDto);

 void saveSeller(Seller seller);

 String updateSeller(SellerDto sellerDto);  // !!!can specify

 List<ProductDto> getProducts();


}
