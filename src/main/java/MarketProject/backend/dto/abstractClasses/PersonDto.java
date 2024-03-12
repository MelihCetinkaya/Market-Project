package MarketProject.backend.dto.abstractClasses;

import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Market;
import MarketProject.backend.entity.Notification;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public abstract class PersonDto {


    private Long id;

    private String name;

    private String surname;

    private int age;

    private Date joined_at;

    private List<Notification> notification_sent = new ArrayList<>();

    private List<Notification>notification_received = new ArrayList<>();

    private List <Comment> comments_received = new ArrayList<>();

   // private List <Comment> commented = new ArrayList<>();
}
