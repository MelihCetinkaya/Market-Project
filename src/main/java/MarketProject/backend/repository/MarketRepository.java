package MarketProject.backend.repository;

import MarketProject.backend.dto.MarketDto;
import MarketProject.backend.entity.Market;
import MarketProject.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long> {

    @Query("SELECT m From Market m WHERE m.marketName = :marketName ")
    Optional <Market> findByMarketName(@Param("marketName") String marketName);


    @Query("Select m.marketName From Market m where m.seller.username=:username")
    List<String> findSellerMarkets(@Param("username")String username);

    @Query("Select m From Market m ")
    List<Market>getMarkets();

}
