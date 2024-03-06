package MarketProject.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import MarketProject.backend.entity.Market;
import java.util.Date;
import java.util.List;

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

    @OneToOne
    private Market market;

    private Date created_at;

}
