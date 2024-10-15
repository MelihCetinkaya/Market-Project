package MarketProject.backend.repository;

import MarketProject.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository //not necessary

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.productId = :productId") //kullanımları long yerine id denediğimden fazlalaştı normalde
    //crud repositoryden findbyıd metodu vardı,değiştirilebilir ikisi
    Product findProductById(@Param("productId")Long productId);

    @Query("SELECT p From Product p Where p.market.marketName=:marketName")
    List<Product> findSellerProductBYMarketName(@Param("marketName")String marketName);

}
