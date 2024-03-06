package MarketProject.backend.dto;

import MarketProject.backend.entity.enums.CommentType;
import MarketProject.backend.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {


    private Long commentId;

    private String comment_expression;

    private Product product;

    private CommentType commentType;

    private Date added_at;

    private Date updated_at;

}
