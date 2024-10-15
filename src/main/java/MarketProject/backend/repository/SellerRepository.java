package MarketProject.backend.repository;


import MarketProject.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerRepository extends PersonRepository<Seller> {

    @Query("SELECT s FROM Seller s WHERE s.username = :username AND s.password = :password")
    Seller findSellerByUsernameAndPassword(@Param("username") String username,
                                               @Param("password") String password);

    @Query("SELECT s From Seller s WHERE s.username = :username ")
    Seller findSellerByUsername(@Param("username") String username);

    Optional<Seller>findByUsername(String username);
}
