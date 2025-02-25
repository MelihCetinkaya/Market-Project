package MarketProject.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Admin {

    @Id
    private final String adminId;

    private final String adminName;

}
