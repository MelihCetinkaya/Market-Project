package MarketProject.backend.entity.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    ROLE_SELLER("SELLER"),
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_ADMIN("ADMIN");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return name();
    }


}
