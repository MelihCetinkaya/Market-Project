package MarketProject.backend.dto;

import MarketProject.backend.entity.enums.NotificationType;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {

    private Long notification_id;

    private String notification_message;

    private NotificationType notificationType;

    private Date notification_date;

}
