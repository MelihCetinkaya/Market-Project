package MarketProject.backend.entity.abstractClasses;

import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Notification;
import MarketProject.backend.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public  abstract class  Person implements UserDetails {

    // username, password

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, name = "Name")
    private String name;

    @Column(length = 20, name = "Surname")
    private String surname;

    @Column(length = 20, name = "Username")
    private String username;

    @Column(length = 300,name = "Password")
    private String password;

    @Column(name = "Age")
    private int age;

    @OneToMany
    private List<Notification>notification_sent = new ArrayList<>();

    @OneToMany
    private List<Notification>notification_received = new ArrayList<>();

    @OneToMany
    private List <Comment> comments_received = new ArrayList<>();

    private Date joined_at;


    @Column(length = 6, name = "Balance")
    private int balance;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
