package MarketProject.backend.entity;

import MarketProject.backend.entity.abstractClasses.Person;
import MarketProject.backend.entity.enums.NotificationRelation;
import MarketProject.backend.entity.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notification_id;

    private String notification_message;

    private NotificationType notificationType;

    private NotificationRelation notificationRelation;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Person notified_by;

    @ManyToOne
    private Person notified_to;

    private Date notification_date;

    private Date created_at;

}
