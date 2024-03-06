package MarketProject.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marketId;

    private String marketName;

    @OneToMany
    private List<Notification>notification_bar;
    @OneToMany
    private List<Product> products;

    @OneToMany
    private List<Comment> comments;

    private Date opening_time;

    private Date closing_time;
}
