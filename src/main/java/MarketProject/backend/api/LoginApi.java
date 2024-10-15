package MarketProject.backend.api;

import MarketProject.backend.api.exceptionApi.exceptions.AlreadyRegisteredUsernameException;
import MarketProject.backend.configuration.AuthenticationService;
import MarketProject.backend.configuration.UserRequest;
import MarketProject.backend.configuration.UserResponse;
import MarketProject.backend.dto.CustomerDto;
import MarketProject.backend.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginApi {

    private final AuthenticationService authenticationService;


    @PostMapping("/saveSeller")
    public ResponseEntity<UserResponse> saveSeller(@RequestBody SellerDto sellerDto) throws AlreadyRegisteredUsernameException {

        return ResponseEntity.ok(authenticationService.saveSeller(sellerDto));
    }

    @PostMapping("/saveCustomer")
    public ResponseEntity<UserResponse> saveCustomer(@RequestBody CustomerDto customerDto) throws AlreadyRegisteredUsernameException {

        return ResponseEntity.ok(authenticationService.saveCustomer(customerDto));
    }

    @PostMapping("/authSeller")
    public ResponseEntity<UserResponse> authSeller(@RequestBody UserRequest userRequest){
       return ResponseEntity.ok(authenticationService.authSeller(userRequest));

    }

    @PostMapping("/authCustomer")
    public ResponseEntity<UserResponse> authCustomer(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authenticationService.authCustomer(userRequest));

    }

}
