package MarketProject.backend.entity;

import MarketProject.backend.entity.abstractClasses.Person;
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
public class Customer extends Person {


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name="Customer_comment",joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns=@JoinColumn ( name = "commmentId") )

    private List<Comment>comments = new ArrayList<>();





}
