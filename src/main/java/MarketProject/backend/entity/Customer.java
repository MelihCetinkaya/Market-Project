package MarketProject.backend.entity;

import java.util.Date;
import java.util.List;

public class Customer {

    private Long customer_id;
    private String customer_name;
    private String customer_surname;

    private int customer_age;
    private List<Comment>comments;
    private Date joined_at;

}
