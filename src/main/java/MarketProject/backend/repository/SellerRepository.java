package MarketProject.backend.repository;

import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s WHERE s.username = :username AND s.password = :password")
    Seller findSellerByUsernameAndPassword(@Param("username") String username,
                                               @Param("password") String password);

}
