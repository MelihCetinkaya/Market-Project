package MarketProject.backend.repository;

import MarketProject.backend.entity.Customer;
import MarketProject.backend.entity.abstractClasses.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {

    //T findByUsername(String username);

    Optional<T> findByUsername(String username);
}
