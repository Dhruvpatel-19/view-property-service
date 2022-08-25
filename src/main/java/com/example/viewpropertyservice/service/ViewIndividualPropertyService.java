package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.AddressDTO;
import com.example.viewpropertyservice.dto.CategoryDTO;
import com.example.viewpropertyservice.dto.FlatAmenitiesDTO;
import com.example.viewpropertyservice.dto.ImageDTO;
import com.example.viewpropertyservice.dto.SocietyAmenitiesDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.dto.TypeDTO;
import com.example.viewpropertyservice.entity.FlatAmenities;
import com.example.viewpropertyservice.entity.Image;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.SocietyAmenities;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewIndividualPropertyService {

  public PropertyDTO convertAllThePropertyAttributesToDto(Property property) {
    PropertyDTO PropertyDTO = new PropertyDTO();
    PropertyDTO.setPropertyName(property.getPropertyName());
    PropertyDTO.setPrice(property.getPrice());
    PropertyDTO.setArea(property.getArea());
    PropertyDTO.setAgeYears(property.getAgeYears());
    PropertyDTO.setAction(property.getAction());
    PropertyDTO.setFurnishing(property.getFurnishing());
    PropertyDTO.setAvailableTo(property.getAvailableTo());
    PropertyDTO.setAvailableFrom(property.getAvailableFrom());
    PropertyDTO.setCreatedAt(property.getCreatedAt());
    PropertyDTO.setImages(listOfImagesWithoutId(property));
    PropertyDTO.setSocietyAmenities(listOfStringAsSocietyAmenitiesWithoutId(property));
    PropertyDTO.setFlatAmenities(listOfStringAsFlatAmenitiesWithoutId(property));
    PropertyDTO.setCategory(convertToCategoryDTO(property));
    PropertyDTO.setType(convertToTypeDTO(property));
    PropertyDTO.setAddress(convertToAddressDTO(property));
    return PropertyDTO;
  }

  public List<ImageDTO> listOfImagesWithoutId(Property property) {

    List<ImageDTO> imageDTOList = new ArrayList<>();
    for (Image image : property.getImages()) {
      ImageDTO imageDTO = new ImageDTO();
      imageDTO.setImage(image.getImage());
      imageDTOList.add(imageDTO);
    }

    return imageDTOList;
  }

  public List<FlatAmenitiesDTO> listOfStringAsFlatAmenitiesWithoutId(Property property) {

    List<FlatAmenitiesDTO> flatAmenitiesDTOList = new ArrayList<>();
    for (FlatAmenities flatAmenities : property.getFlatAmenities()) {
      FlatAmenitiesDTO flatAmenitiesDTO = new FlatAmenitiesDTO();
      flatAmenitiesDTO.setName(flatAmenities.getName());
      flatAmenitiesDTOList.add(flatAmenitiesDTO);
    }

    return flatAmenitiesDTOList;

  }

  public List<SocietyAmenitiesDTO> listOfStringAsSocietyAmenitiesWithoutId(Property property) {

    List<SocietyAmenitiesDTO> societyAmenitiesDTOList = new ArrayList<>();
    for (SocietyAmenities societyAmenities : property.getSocietyAmenities()) {
      SocietyAmenitiesDTO societyAmenitiesDTO = new SocietyAmenitiesDTO();
      societyAmenitiesDTO.setName(societyAmenities.getName());
      societyAmenitiesDTOList.add(societyAmenitiesDTO);
    }

    return societyAmenitiesDTOList;

  }


  public AddressDTO convertToAddressDTO(Property property) {

    AddressDTO addressDTO=new AddressDTO();
    addressDTO.setStreetLine(property.getAddress().getStreetLine());
    addressDTO.setAdditionalStreet(property.getAddress().getAdditionalStreet());
    addressDTO.setCity(property.getAddress().getCity());
    addressDTO.setState(property.getAddress().getState());
    addressDTO.setPostCode(property.getAddress().getPostCode());

    return addressDTO;

  }


  public CategoryDTO convertToCategoryDTO(Property property){
    CategoryDTO categoryDTO=new CategoryDTO();
    categoryDTO.setCategory(property.getCategory().getCategory());
    return categoryDTO;
  }
  public TypeDTO convertToTypeDTO(Property property){

    TypeDTO typeDTO=new TypeDTO();
    typeDTO.setType(property.getType().getType());
    return typeDTO;

  }


}
