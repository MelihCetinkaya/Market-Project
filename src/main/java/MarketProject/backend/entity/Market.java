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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long marketId;

    private String marketName;

    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Notification>notification_bar;

    @OneToMany(cascade = {CascadeType.REMOVE})
    //@JoinColumn(name = "addedProduct")
    private List<Product> products;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> marketComments;

    private Date opening_time;

    private Date closing_time;
}
