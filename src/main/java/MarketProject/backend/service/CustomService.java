package MarketProject.backend.service;

import MarketProject.backend.api.exceptionApi.exceptions.AccessRestrictionException;
import MarketProject.backend.api.exceptionApi.exceptions.InsufficientBalanceException;
import MarketProject.backend.api.exceptionApi.exceptions.ProductNotFoundException;
import MarketProject.backend.dto.ProductDto;
import MarketProject.backend.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface CustomService {

    String addToCart (String token,String username,Long productId) throws ProductNotFoundException, AccessRestrictionException;

    String confirmTransaction(String token,String username) throws InsufficientBalanceException, AccessRestrictionException;
}
