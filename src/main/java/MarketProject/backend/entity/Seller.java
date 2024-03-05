package MarketProject.backend.entity;

import jakarta.persistence.Column;
import lombok.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 20, name = "Name")
    private String name;
    @Column(length = 20, name = "Surname")
    private String surname;
    @Column(name = "Age")
    private int age;
    private String marketName;

    private Date created_at;

}
