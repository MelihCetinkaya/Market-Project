package MarketProject.backend.api;

import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Product;
import MarketProject.backend.service.CustomerService;
import MarketProject.backend.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApi {

    private final SellerService sellerService;
    private final CustomerService customerService;
    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>>getProducts(){
        return ResponseEntity.ok(sellerService.getProducts());
    }


    @GetMapping("/choose/{id}")
    public ResponseEntity<Optional<Product>> chooseProduct(@PathVariable Long id){
        return ResponseEntity.ok(sellerService.getProduct(id));
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> makeComment(String expression){

         return ResponseEntity.ok(customerService.makeComment(expression));

    }
}
