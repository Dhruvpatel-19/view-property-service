package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.*;
import com.example.viewpropertyservice.entity.*;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.repository.FavouritesRepository;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ViewPropertyService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PropertyRepository propertyRepository;

  @Autowired
  private FavouritesRepository favouritesRepository;

  public String addToFavourite(HttpServletRequest request , int propertyId) throws Exception {
    String requestTokenHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String email = null;
    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try{
        email = jwtUtil.extractUsername(jwtToken);
      }catch (Exception e){
        throw new Exception("User not found exception");
      }

      Property property = propertyRepository.findByPropertyId(propertyId);
      User user = userRepository.findByEmail(email);
      List<Favourites> favouritesList = user.getFavPropertyList();

      Favourites favourites = new Favourites();
      favourites.setProperty(property);
      favourites.setUser(user);

      favouritesList.add(favourites);

      user.setFavPropertyList(favouritesList);

      favouritesRepository.save(favourites);
      userRepository.save(user);

      return "added to favourite successfully";
    }
    return "some error occurred while addFavourite";
  }

  public String removeFromFavourite(HttpServletRequest request , int propertyId) throws Exception{
    String requestTokenHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String email = null;
    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        email = jwtUtil.extractUsername(jwtToken);
      } catch (Exception e) {
        throw new Exception("User not found exception");
      }
      User user = userRepository.findByEmail(email);

      Property property = propertyRepository.findByPropertyId(propertyId);

      favouritesRepository.deleteByUserAndProperty( user , property);

      return "remove from favourite list";
     }
    return "some error occurred while removeFromFavourite";
  }

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
