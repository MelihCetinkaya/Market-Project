package MarketProject.backend.entity.abstractClasses;

import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Notification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  abstract class  Person {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, name = "Name")
    private String name;
    @Column(length = 20, name = "Surname")
    private String surname;
    @Column(name = "Age")
    private int age;

    @OneToMany

    private List<Notification>notification_sent = new ArrayList<>();

    @OneToMany
    private List<Notification>notification_received = new ArrayList<>();

    @OneToMany
    private List <Comment> comments_received = new ArrayList<>();

    private Date joined_at;

}
