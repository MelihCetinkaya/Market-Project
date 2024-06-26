package MarketProject.backend.api;

import MarketProject.backend.dto.*;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerApi {

    private final SellerService sellerService;


    @GetMapping("/login")
    public ResponseEntity<SellerDto> login(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(sellerService.login(username, password)) ;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>>getProducts(){


    return ResponseEntity.ok(sellerService.getProducts());

    }

   @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){

        return ResponseEntity.ok(sellerService.addProduct(productDto));

    }

    @PostMapping("/save")
    public ResponseEntity<Seller> saveSeller(@RequestBody SellerDto sellerDto){

        return ResponseEntity.ok(sellerService.saveSeller(sellerDto));
    }

    @GetMapping("/product/{product_id}")
    public ResponseEntity<Product>getProduct(Long product_id){
        return ResponseEntity.ok(sellerService.getProduct(product_id));
    }

    public ResponseEntity<List<CommentDto>> getMarketComments(){

        return ResponseEntity.ok(sellerService.getMarketComments());
    }

    public ResponseEntity<List<NotificationDto>> getMarketNotifications(){
        return ResponseEntity.ok(sellerService.getMarketNotifications());
    }
}
