package MarketProject.backend.dto;

import MarketProject.backend.entity.Comment;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerDto {

    private Long customer_id;
    private String customer_name;
    private String customer_surname;

    private int customer_age;

    private List<Comment> comments;
    private Date joined_at;


}
