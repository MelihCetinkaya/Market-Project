package MarketProject.backend.dto;

import MarketProject.backend.dto.abstractClasses.PersonDto;
import MarketProject.backend.entity.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@EqualsAndHashCode(callSuper = true)//****
@Data
public class CustomerDto extends PersonDto {

    private List<Comment>comments = new ArrayList<>();

    private boolean accountNonExpired =true;
    private boolean isEnabled =true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;






    Map<String, Integer> addedProducts = new HashMap<>();
}
