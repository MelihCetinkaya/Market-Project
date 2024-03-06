package MarketProject.backend.dto;

import MarketProject.backend.entity.Market;
import lombok.Data;

import java.util.Date;
@Data
public class SellerDto {

    private Long id;
    private String name;
    private String surname;

    private int age;
    private String marketName;

    private Market market;
    private Date created_at;

}
