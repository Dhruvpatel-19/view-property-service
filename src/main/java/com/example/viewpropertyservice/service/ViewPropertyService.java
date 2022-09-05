package com.example.viewpropertyservice.service;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.dto.FavouriteDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.Owner;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.User;
import com.example.viewpropertyservice.exception.*;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.mapstruct.MapStructMapper;
import com.example.viewpropertyservice.repository.FavouritesRepository;
import com.example.viewpropertyservice.repository.OwnerRepository;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
    if(!propertyRepository.existsById(propertyId)){
      throw new PropertyNotFoundException();
    }
    Property property = propertyRepository.findByPropertyId(propertyId);
    return mapStructMapper.propertyToPropertyDto(property);
  }

  public FavouriteDTO addToFavourite(HttpServletRequest request , int propertyId){
    User user = (User) getOwnerOrUser(request);
    if(user==null) {
      throw new UserNotFoundException();
    }

    if(!propertyRepository.existsById(propertyId)) {
      throw new PropertyNotFoundException();
    }

    Property property = propertyRepository.findByPropertyId(propertyId);

    if(favouritesRepository.existsByUserAndProperty(user , property)){
      throw new PropertyAlreadyInFavouriteException();
    }

    List<Favourites> favouritesList = user.getFavPropertyList();

    Favourites favourites = new Favourites();
    favourites.setProperty(property);
    favourites.setUser(user);
    favouritesList.add(favourites);
    user.setFavPropertyList(favouritesList);

    favouritesRepository.save(favourites);
    userRepository.save(user);
    return mapStructMapper.favouriteToFavouriteDTO(favourites);

  }

  public FavouriteDTO removeFromFavourite(HttpServletRequest request , int propertyId){
    User user = (User) getOwnerOrUser(request);
    if(user==null){
      throw new UserNotFoundException();
    }

    if(!propertyRepository.existsById(propertyId)){
      throw new PropertyNotFoundException();
    }

    Property property = propertyRepository.findByPropertyId(propertyId);

    if(!favouritesRepository.existsByUserAndProperty(user,property)){
      throw new PropertyNotExistsInFavouriteException();
    }

    Favourites favourites = favouritesRepository.findByUserAndProperty(user , property);
    favouritesRepository.deleteByUserAndProperty( user , property);
    return mapStructMapper.favouriteToFavouriteDTO(favourites);

  }

  public List<AllPropertyDTO> getAllFavourite(HttpServletRequest request){
    User user = (User) getOwnerOrUser(request);
    if(user==null){
      throw new UserNotFoundException();
    }
    List<Property> propertyList = new ArrayList<>();

    List<Favourites> favouritesList = user.getFavPropertyList();
    for(Favourites favourites : favouritesList){
      propertyList.add(favourites.getProperty());
    }
    return propertyList.stream().map(property -> mapStructMapper.propertyToAllPropertyDto(property)).collect(Collectors.toList());

  }

  private Object getOwnerOrUser(HttpServletRequest request){
    String requestTokenHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String email = null;

    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
      jwtToken = requestTokenHeader.substring(7);

      try{
        email = jwtUtil.extractUsername(jwtToken);
      }catch (ExpiredJwtException e){
        throw new JwtTokenExpiredException();
      }catch (SignatureException | MalformedJwtException e){
        throw new JwtSignatureException();
      } catch (Exception e){
        return null;
      }

      User user = userRepository.findByEmail(email);
      Owner owner = ownerRepository.findByEmail(email);

      if(user == null && owner==null)
        throw new UserNotFoundException();

      if(user!=null)
        return user;
      else
        return owner;
    }
    return null;
  }

}
