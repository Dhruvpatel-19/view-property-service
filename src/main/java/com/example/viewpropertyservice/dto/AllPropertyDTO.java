package com.example.viewpropertyservice.dto;

import com.example.viewpropertyservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllPropertyDTO {

    private int propertyId;
    private String propertyName;
    private String price;
    private String area;
    private String image;
    private boolean isSold;
    private Address address;

}