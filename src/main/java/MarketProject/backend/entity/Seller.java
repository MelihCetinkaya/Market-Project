package MarketProject.backend.entity;

import MarketProject.backend.entity.abstractClasses.Person;
import jakarta.persistence.*;
import lombok.*;



import java.util.*;



@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller extends Person  {



    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name="Marketlerim",joinColumns = @JoinColumn(name = "id")
            ,inverseJoinColumns = @JoinColumn(name="marketId"))
    private List<Market> markets;

    @OneToMany
    private List <Comment> commented = new ArrayList<>();




}
