package MarketProject.backend.repository;

import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.enums.CommentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.password = :password")
    Customer findCustomerByUsernameAndPassword(@Param ("username") String username,
                                                 @Param("password") String password);
}
