package MarketProject.backend.entity;

import java.util.Date;
import java.util.List;

public class Product {

    private Long productId;
    private String productName;
    private int stock_amount;
    private Boolean stock_status;
    private List<Comment> comments;
    private Date added_at;
    private Date supplyDate;
}
