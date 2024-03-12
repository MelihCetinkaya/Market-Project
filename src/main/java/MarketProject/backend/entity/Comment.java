package MarketProject.backend.entity;

import MarketProject.backend.entity.enums.CommentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private String comment_expression;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer commented_by;

    private CommentType commentType;

    @Temporal(TemporalType.DATE)
    private Date added_at;

    @Temporal(TemporalType.DATE)
    private Date updated_at;


}
