package MarketProject.backend.entity;

import MarketProject.backend.entity.abstractClasses.Person;
import MarketProject.backend.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person  {

    @Column(length = 20, name = "AddedProducts")
    @ElementCollection
    Map<String, Integer> addedProducts = new HashMap<>();


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name="Customer_comment",joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns=@JoinColumn ( name = "commmentId") )

    private List<Comment>comments = new ArrayList<>();









}
