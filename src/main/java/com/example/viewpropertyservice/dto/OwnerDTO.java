package com.example.viewpropertyservice.dto;

import lombok.Data;

@Data
public class OwnerDTO {
    private int ownerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
