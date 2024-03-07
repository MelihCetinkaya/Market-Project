package MarketProject.backend.dto;

import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.enums.NotificationRelation;
import MarketProject.backend.entity.enums.NotificationType;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {

    private Long notification_id;

    private String notification_message;

    private NotificationType notificationType;

    private NotificationRelation notificationRelation;

    private Product product;

    private Date notification_date;

    private Date created_at;

}
