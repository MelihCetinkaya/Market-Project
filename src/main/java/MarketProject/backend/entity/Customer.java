package MarketProject.backend.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;


@Entity
public class Customer {

    @Id
    private Long customer_id;
    private String customer_name;
    private String customer_surname;

    private int customer_age;
    @OneToMany
    private List<Comment>comments;
    private Date joined_at;

}
