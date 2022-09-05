package com.example.viewpropertyservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteDTO {
    private int favId;
    private UserDTO user;
    private PropertyDTO property;
}
