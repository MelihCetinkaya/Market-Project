package MarketProject.backend.dto;

import MarketProject.backend.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDto {

    private Long productId;
    private String productName;
    private int stock_amount;
    private Boolean stock_status;
    private List<String> comments;
    private Date added_at;
    private Date supplyDate;

}
