package MarketProject.backend.entity;

import java.util.Date;
import java.util.List;

public class Market {

    private Long marketId;

    private String marketName;

    private List<Notification>notification_bar;

    private List<Product> products;

    private Date opening_time;

    private Date closing_time;
}
