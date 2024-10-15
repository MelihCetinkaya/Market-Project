package MarketProject.backend.dto;

import MarketProject.backend.dto.abstractClasses.PersonDto;
import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.Market;

import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)//*****
@Data
public class SellerDto extends PersonDto {


    private Market market;

    private List <Comment> commented = new ArrayList<>();

    private boolean accountNonExpired =true;
    private boolean isEnabled =true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;



}
