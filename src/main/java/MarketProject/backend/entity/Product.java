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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String productName;

    private int stock_amount;

    private Boolean stock_status;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "Product-Comment", joinColumns = {

            @JoinColumn(name = "productId"),


    },
            inverseJoinColumns = @JoinColumn(name = "comment_expression")

    )
    @JoinColumn(name="commentId")
    private List<Comment> comments;

    @OneToMany
    @JoinTable(name="bildirimler",joinColumns = @JoinColumn(name="productId"),
            inverseJoinColumns = @JoinColumn(name="notification_id"))
    private List<Notification>notifications;

    @ManyToOne
    private Market market;

    @Temporal(TemporalType.DATE)
    private Date added_at;

    @Temporal(TemporalType.DATE)
    private Date supplyDate;

    @Column(length = 5, name = "Price")
    private int price;

}
