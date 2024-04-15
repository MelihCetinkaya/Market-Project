package MarketProject.backend.api.exceptionApi;

import MarketProject.backend.api.exceptionApi.exceptions.PersonNotFoundException;
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
}
