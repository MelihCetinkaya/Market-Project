package MarketProject.backend.entity;

import MarketProject.backend.entity.abstractClasses.Person;
import jakarta.persistence.*;
import lombok.*;
import MarketProject.backend.entity.Market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Seller extends Person {



    @OneToOne
    private Market market = null;

    @OneToMany
    private List <Comment> commented = new ArrayList<>();

}
