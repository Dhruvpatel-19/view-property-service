package com.example.viewpropertyservice.dto;

import lombok.Data;

@Data
public class AddressDTO {

  private String streetLine;
  private String additionalStreet;
  private String city;
  private String state;
  private int postCode;


}
