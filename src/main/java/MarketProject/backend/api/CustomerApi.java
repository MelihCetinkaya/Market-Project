package MarketProject.backend.api;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.api.exceptionApi.exceptions.PersonNotFoundException;
import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.MarketDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Product;
import MarketProject.backend.service.CustomerService;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.CacheUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApi {

    private final SellerService sellerService;
    private final CustomerService customerService;

    @PostMapping("/comment")
    public ResponseEntity<Comment> makeComment(String expression){

        return ResponseEntity.ok(customerService.makeComment(expression));

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<CommentDto>>getProductComments(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getComments(id));
    }

    @GetMapping(value="/notification/{username}",consumes = MediaType.ALL_VALUE)
    public SseEmitter createNotification(@RequestHeader("Authorization") String token,
                                   @PathVariable String username,
                                   @RequestParam Long product_id){
       return customerService.createNotification(token,username,product_id);
    }

    @PostMapping("/dispatchEvents")
    public void dispatchEventsToClients(@RequestParam Long product_id){

        customerService.dispatchEvents(product_id);

    }

    @PostMapping("/feedback")
    public void makeFeedback(String expression){
     customerService.makeFeedback(expression);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>>getProducts(){
        return ResponseEntity.ok(customerService.getProducts());
    }


    @GetMapping("/choose/{id}")
    public ResponseEntity<Product> chooseProduct(@PathVariable Long id){
        return ResponseEntity.ok(sellerService.getProduct(id));
    }

    @GetMapping("/markets")
    public ResponseEntity<List<MarketDto>> getMarkets(){
        return ResponseEntity.ok(customerService.getMarkets());
    }

}
