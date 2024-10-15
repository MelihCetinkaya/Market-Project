package MarketProject.backend.api;

import MarketProject.backend.api.exceptionApi.exceptions.AccessRestrictionException;
import MarketProject.backend.api.exceptionApi.exceptions.InsufficientBalanceException;
import MarketProject.backend.api.exceptionApi.exceptions.ProductNotFoundException;
import MarketProject.backend.api.exceptionApi.exceptions.UnmatchedPersonException;
import MarketProject.backend.configuration.AuthenticationService;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.service.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custom")
@RequiredArgsConstructor
public class CustomApi {

    private final CustomService customService;

    private final AuthenticationService authenticationService;
    @GetMapping("/myPage/{username}")
    public ResponseEntity<Customer>getMyPage(@RequestHeader("Authorization") String token,@PathVariable String username) throws UnmatchedPersonException {

        return ResponseEntity.ok(authenticationService.getCustomDetails(token,username));

    }

    @PostMapping("/addToCart/{username}")
    public ResponseEntity<String>addToCart(@RequestHeader("Authorization") String token,@PathVariable String username,@RequestParam Long productId) throws ProductNotFoundException, AccessRestrictionException {
        return ResponseEntity.ok(customService.addToCart(token,username,productId));
    }

    @PostMapping("/confirm/{username}")
    public ResponseEntity<String> confirmTransaction(@RequestHeader("Authorization") String token,@PathVariable String username) throws InsufficientBalanceException, AccessRestrictionException {

        return ResponseEntity.ok(customService.confirmTransaction(token,username));
    }
}
