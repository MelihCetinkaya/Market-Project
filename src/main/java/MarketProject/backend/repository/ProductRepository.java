package MarketProject.backend.repository;

import MarketProject.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository //not necessary

public interface ProductRepository extends JpaRepository<Product,Long> {

}
