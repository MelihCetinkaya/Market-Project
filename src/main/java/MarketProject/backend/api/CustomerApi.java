package MarketProject.backend.api;

import MarketProject.backend.dto.CommentDto;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Product;
import MarketProject.backend.service.CustomerService;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApi {

    private final SellerService sellerService;
    private final CustomerService customerService;

    @PostMapping("/save")
    public void saveCustomer(@RequestBody CustomerDto customerDto){

        customerService.saveCustomer(customerDto);

    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> makeComment(String expression){

        return ResponseEntity.ok(customerService.makeComment(expression));

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<CommentDto>>getProductComments(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getComments(id));
    }

    @PostMapping("/notification/{product_id}")
    public void createNotification(@PathVariable Long product_id, Date date){
        customerService.createNotification(product_id,date);
    }

    @PostMapping("/feedback")
    public void makeFeedback(String expression){
     customerService.makeFeedback(expression);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>>getProducts(){
        return ResponseEntity.ok(sellerService.getProducts());
    }


    @GetMapping("/choose/{id}")
    public ResponseEntity<Product> chooseProduct(@PathVariable Long id){
        return ResponseEntity.ok(sellerService.getProduct(id));
    }


}
