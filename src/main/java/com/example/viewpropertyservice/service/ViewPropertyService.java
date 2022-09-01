package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.*;
import com.example.viewpropertyservice.entity.*;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.repository.FavouritesRepository;
import com.example.viewpropertyservice.repository.OwnerRepository;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ViewPropertyService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private PropertyRepository propertyRepository;

  @Autowired
  private FavouritesRepository favouritesRepository;

  public Property getPropertyById(int propertyId){
    return propertyRepository.findByPropertyId(propertyId);
  }

  public String addToFavourite(HttpServletRequest request , int propertyId) throws Exception {
    User user = (User) getOwnerOrUser(request);
    if(user !=null) {
      Property property = propertyRepository.findByPropertyId(propertyId);
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
    User user = (User) getOwnerOrUser(request);
    if(user!=null){
      Property property = propertyRepository.findByPropertyId(propertyId);
      favouritesRepository.deleteByUserAndProperty( user , property);
      return "remove from favourite list";
    }
    return "some error occurred while removeFromFavourite";
  }

  public List<AllPropertyDTO> getAllFavourite(HttpServletRequest request) throws Exception {
    User user = (User) getOwnerOrUser(request);
    List<Property> propertyList = new ArrayList<>();
    if(user!=null){
     List<Favourites> favouritesList = user.getFavPropertyList();
        for(Favourites favourites : favouritesList){
          propertyList.add(favourites.getProperty());
        }
      return propertyList.stream().map(this::toAllPropertyDTO).collect(Collectors.toList());
    }
    return null;
  }

  private AllPropertyDTO toAllPropertyDTO(Property property){

    AllPropertyDTO allPropertyDTO = new AllPropertyDTO();

    allPropertyDTO.setPropertyId(property.getPropertyId());
    allPropertyDTO.setPropertyId(property.getPropertyId());
    allPropertyDTO.setPropertyName(property.getPropertyName());
    allPropertyDTO.setPrice(property.getPrice());
    allPropertyDTO.setArea(property.getArea());
    allPropertyDTO.setImage(property.getImages().get(0).getImage());
    allPropertyDTO.setAddress(property.getAddress());

    return allPropertyDTO;
  }

  private Object getOwnerOrUser(HttpServletRequest request) throws Exception {
    String requestTokenHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String email = null;

    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
      jwtToken = requestTokenHeader.substring(7);
      try {
        email = jwtUtil.extractUsername(jwtToken);
      }catch (Exception e){
        throw new Exception("User not found");
      }

      User user = userRepository.findByEmail(email);
      Owner owner = ownerRepository.findByEmail(email);
      if(user!=null)
        return user;
      else
        return owner;
    }
    return null;
  }


}
