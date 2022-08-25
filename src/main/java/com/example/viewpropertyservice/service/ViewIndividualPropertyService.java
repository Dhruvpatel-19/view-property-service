package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.AddressDTO;
import com.example.viewpropertyservice.dto.FlatAmenitiesDTO;
import com.example.viewpropertyservice.dto.ImageDTO;
import com.example.viewpropertyservice.dto.SocietyAmenitiesDTO;
import com.example.viewpropertyservice.dto.ViewPropertyInformationDto;
import com.example.viewpropertyservice.entity.FlatAmenities;
import com.example.viewpropertyservice.entity.Image;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.SocietyAmenities;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewIndividualPropertyService {

  public ViewPropertyInformationDto convertAllThePropertyAttributesToDto(Property property) {
    ViewPropertyInformationDto viewPropertyInformationDto = new ViewPropertyInformationDto();
    viewPropertyInformationDto.setPropertyName(property.getPropertyName());
    viewPropertyInformationDto.setPrice(property.getPrice());
    viewPropertyInformationDto.setArea(property.getArea());
    viewPropertyInformationDto.setAgeYears(property.getAgeYears());
    viewPropertyInformationDto.setAction(property.getAction());
    viewPropertyInformationDto.setFurnishing(property.getFurnishing());
    viewPropertyInformationDto.setAvailableTo(property.getAvailableTo());
    viewPropertyInformationDto.setAvailableFrom(property.getAvailableFrom());
    viewPropertyInformationDto.setCreatedAt(property.getCreatedAt());
    viewPropertyInformationDto.setImages(listOfImagesWithoutId(property));
    viewPropertyInformationDto.setSocietyAmenities(listOfStringAsSocietyAmenitiesWithoutId(property));
    viewPropertyInformationDto.setFlatAmenities(listOfStringAsFlatAmenitiesWithoutId(property));
    viewPropertyInformationDto.setCategory(property.getCategory().getCategory());
    viewPropertyInformationDto.setType(property.getType().getType());
    viewPropertyInformationDto.setAddress(convertToAddressDTO(property));
    return viewPropertyInformationDto;
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


}
