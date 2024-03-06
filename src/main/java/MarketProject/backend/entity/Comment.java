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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment_expression;
    @ManyToOne
    private Product product;

   private CommentType commentType;

    private Date added_at;

    private Date updated_at;


}
