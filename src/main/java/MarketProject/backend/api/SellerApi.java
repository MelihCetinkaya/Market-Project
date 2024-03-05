package MarketProject.backend.api;

import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Product;
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



    @GetMapping
    public List<Product>getProducts(){

    return null;

    }

   @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){

        return ResponseEntity.ok(sellerService.addProduct(productDto));

    }
}
