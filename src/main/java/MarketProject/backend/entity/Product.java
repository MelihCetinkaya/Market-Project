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

    /*@ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;*/

    @Temporal(TemporalType.DATE)
    private Date added_at;

    @Temporal(TemporalType.DATE)
    private Date supplyDate;
}
