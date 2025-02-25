package MarketProject.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    private Seller seller;

    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Notification>notification_bar;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name="Market-Ürünleri",joinColumns = @JoinColumn(name = "marketId")
            ,inverseJoinColumns = @JoinColumn(name="productId"))
    //@JoinColumn(name = "addedProduct")
    private List<Product> products;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> marketComments;

    private Date opening_time;

    private Date closing_time;
}
