package MarketProject.backend.entity;

import MarketProject.backend.entity.enums.NotificationRelation;
import MarketProject.backend.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notification_id;

    private String notification_message;

    private NotificationType notificationType;

    private NotificationRelation notificationRelation;

    @ManyToOne
    private Product product;// could be change

    private Date notification_date;

    private Date created_at;

}
