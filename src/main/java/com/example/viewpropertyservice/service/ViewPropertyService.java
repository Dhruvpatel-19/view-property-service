package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.dto.OwnerDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.Owner;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.User;
import com.example.viewpropertyservice.exceptionhandler.PropertyNotFoundException;
import com.example.viewpropertyservice.exceptionhandler.UserNotFoundException;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.mapstruct.MapStructMapper;
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

  @Autowired
  private MapStructMapper mapStructMapper;

  public PropertyDTO getPropertyById(int propertyId){
    if(propertyRepository.findByPropertyId(propertyId)==null) throw new PropertyNotFoundException();
    Property property = propertyRepository.findByPropertyId(propertyId);
    return mapStructMapper.propertyToPropertyDto(property);
  }

  public String addToFavourite(HttpServletRequest request , int propertyId) throws Exception {
    User user = (User) getOwnerOrUser(request);
    if(user==null) throw new UserNotFoundException();
    if(user !=null) {

      Property property = propertyRepository.findByPropertyId(propertyId);
      if(property ==null) throw new PropertyNotFoundException();

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
    if(user==null) throw new UserNotFoundException();
    if(user!=null){
      Property property = propertyRepository.findByPropertyId(propertyId);
      favouritesRepository.deleteByUserAndProperty( user , property);
      return "remove from favourite list";
    }
    return "some error occurred while removeFromFavourite";
  }

  public List<AllPropertyDTO> getAllFavourite(HttpServletRequest request) throws Exception {
    User user = (User) getOwnerOrUser(request);
    if(user==null) throw new UserNotFoundException();
    List<Property> propertyList = new ArrayList<>();
    if(user!=null){
     List<Favourites> favouritesList = user.getFavPropertyList();
        for(Favourites favourites : favouritesList){
          propertyList.add(favourites.getProperty());
        }
      return propertyList.stream().map(property -> mapStructMapper.propertyToAllPropertyDto(property)).collect(Collectors.toList());
    }
    return null;
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
