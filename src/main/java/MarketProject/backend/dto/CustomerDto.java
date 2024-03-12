package MarketProject.backend.dto;

import MarketProject.backend.dto.abstractClasses.PersonDto;
import MarketProject.backend.entity.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)//****
@Data
public class CustomerDto extends PersonDto {

    private List<Comment>comments = new ArrayList<>();


}
