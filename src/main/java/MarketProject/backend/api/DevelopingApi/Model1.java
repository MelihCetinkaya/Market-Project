package MarketProject.backend.api.DevelopingApi;

import MarketProject.backend.entity.Product;
import MarketProject.backend.entity.abstractClasses.Person;
import MarketProject.backend.entity.enums.NotificationRelation;
import MarketProject.backend.entity.enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


//@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "models")
public class Model1 {


    @Id
    private Long notification_id;

    private String notification_message;

    private NotificationType notificationType;

    private NotificationRelation notificationRelation;


    private Product product;


    private Person notified_by;

    private Person notified_to;

    private Date notification_date;

    private Date created_at;
}
