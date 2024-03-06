package MarketProject.backend.dto;

import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.Product;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MarketDto {

    private Long marketId;

    private String marketName;

    private List<Notification> notification_bar;

    private List<Product> products;

    private List<Comment> comments;

    private Date opening_time;

    private Date closing_time;

}
