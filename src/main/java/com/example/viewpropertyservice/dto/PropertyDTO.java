package com.example.viewpropertyservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class PropertyDTO {
// Use this DTO to view it to users for selling.
  private String propertyName;
  private String price;
  private String area;
  private int ageYears;
  private int action;
  private String furnishing;
  private Date availableTo;
  private Date availableFrom;
  private String parkingAvailability;
  private boolean isSold;
  private LocalDateTime createdAt;
  private List<ImageDTO> images;//list of strings of images instead of List<Image> images
  private List<SocietyAmenitiesDTO> societyAmenities;// instead of List<SocietyAmenities> societyAmenities
  private List<FlatAmenitiesDTO> flatAmenities; //instead of List<FlatAmenities>
  private CategoryDTO category;
  private TypeDTO type;
  private AddressDTO address;
}
