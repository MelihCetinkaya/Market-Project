package MarketProject.backend.api.exceptionApi;

import MarketProject.backend.api.exceptionApi.exceptions.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public String iaException(){
        return "wrong parameter";
    }

    @ExceptionHandler({PersonNotFoundException.class})
    public String personNotFound(){ return  "this person does not exist";}

    @ExceptionHandler({ProductNotFoundException.class})
    public String productNotFound(){ return  "This product is out of stock";}

    @ExceptionHandler({InsufficientBalanceException.class})
    public String InsufficientBalance(){
        return "your balance is insufficient";
    }

    @ExceptionHandler({AlreadyRegisteredUsernameException.class})
    public String usernameAlreadyRegistered(){
        return  "username already exists";
    }

    @ExceptionHandler({UnmatchedPersonException.class})
    public String unmatchedPerson(){ return "you can't update this person";}

    @ExceptionHandler({MarketNotFoundException.class})
    public String marketNotFound(){ return "this market doesn't exit";}

    @ExceptionHandler({AccessRestrictionException.class})
    public String accessDenied(){ return "You do not have permission to access this page.";}
}
