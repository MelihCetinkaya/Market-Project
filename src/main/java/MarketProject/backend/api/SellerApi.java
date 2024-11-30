package MarketProject.backend.api;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.MarketNotFoundException;
import MarketProject.backend.dto.*;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Market;
import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.Seller;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerApi {

    private final SellerService sellerService;

    @GetMapping("/productsOfMarket")
    public ResponseEntity<List<ProductDto>>getProductsOfMarket(@RequestParam String marketName){


    return ResponseEntity.ok(sellerService.getProductsOfMarket(marketName));

    }

   @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto,@RequestParam String marketName) throws MarketNotFoundException {

        //ProductDto ve marketName birleşebilir

        return ResponseEntity.ok(sellerService.addProduct(productDto,marketName));

    }

    @GetMapping("/product/{product_id}")
    public ResponseEntity<Product>getProduct(@PathVariable Long product_id){
        return ResponseEntity.ok(sellerService.getProduct(product_id));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getMarketComments(){

        return ResponseEntity.ok(sellerService.getMarketComments());
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getMarketNotifications(){
        return ResponseEntity.ok(sellerService.getMarketNotifications());
    }

    @PostMapping("/createMarket")
    public ResponseEntity<MarketDto> createMarket(@RequestParam String username,@RequestParam String marketName){


        return ResponseEntity.ok(sellerService.createMarket(username,marketName));
    }

    @GetMapping("/chooseMarket")
    public ResponseEntity<Market> chooseMarket(@RequestParam String marketName) throws MarketNotFoundException {

        return ResponseEntity.ok(sellerService.chooseMArket(marketName));
    }
}
