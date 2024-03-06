package MarketProject.backend.api;

import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.dto.SellerDto;
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



    @GetMapping
    public ResponseEntity<List<ProductDto>>getProducts(){


    return ResponseEntity.ok(sellerService.getProducts());

    }

   @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){

        return ResponseEntity.ok(sellerService.addProduct(productDto));

    }

    @PostMapping("/addUser")
    public ResponseEntity<Seller> saveSeller(SellerDto sellerDto){

        return ResponseEntity.ok(sellerService.saveSeller(sellerDto));
    }
}
