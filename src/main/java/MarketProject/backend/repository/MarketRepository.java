package MarketProject.backend.repository;

import MarketProject.backend.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long> {

    @Query("SELECT m From Market m WHERE m.marketName = :marketName ")
    Optional <Market> findByMarketName(@Param("marketName") String marketName);

}
