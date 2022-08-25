package com.example.viewpropertyservice.dto;

import com.example.viewpropertyservice.entity.Address;
import com.example.viewpropertyservice.entity.Category;
import com.example.viewpropertyservice.entity.FlatAmenities;
import com.example.viewpropertyservice.entity.Image;
import com.example.viewpropertyservice.entity.SocietyAmenities;
import com.example.viewpropertyservice.entity.Type;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ViewPropertyInformationDto {
// Use this DTO to view it to users for selling.
  private String propertyName;
  private String price;
  private String area;
  private int ageYears;
  private int action;
  private String furnishing;
  private Date availableTo;
  private Date availableFrom;
  private LocalDateTime createdAt;
  private List<ImageDTO> images;//list of strings of images instead of List<Image> images
  private List<SocietyAmenitiesDTO> societyAmenities;// instead of List<SocietyAmenities> societyAmenities
  private List<FlatAmenitiesDTO> flatAmenities; //instead of List<FlatAmenities>
  private String category; //from Category object
  private String type; //instead of type id in property entity (private Type type) fetch type from the type table as you have made a mapping to the type table
  private AddressDTO address;

}
