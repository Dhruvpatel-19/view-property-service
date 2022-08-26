package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Category;
import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.FlatAmenities;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.SocietyAmenities;
import com.example.viewpropertyservice.entity.Type;
import com.example.viewpropertyservice.entity.User;
import com.example.viewpropertyservice.repository.AddressRepository;
import com.example.viewpropertyservice.repository.CategoryRepository;
import com.example.viewpropertyservice.repository.FlatAmenitiesRepository;
import com.example.viewpropertyservice.repository.ImageRepository;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.SocietyAmenitiesRepository;
import com.example.viewpropertyservice.repository.TypeRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import com.example.viewpropertyservice.service.ViewIndividualPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/viewPropertyService")
public class ViewIndividualPropertyInformationController {

  @Autowired ViewIndividualPropertyService viewIndividualPropertyService;
  @Autowired PropertyRepository propertyRepository;

  @Autowired CategoryRepository categoryRepository;

  @Autowired TypeRepository typeRepository;

  @Autowired AddressRepository addressRepository;

  @Autowired FlatAmenitiesRepository flatAmenitiesRepository;

  @Autowired SocietyAmenitiesRepository societyAmenitiesRepository;

  @Autowired ImageRepository imageRepository;


  @Autowired UserRepository userRepository;



  @GetMapping("/getProperty/{propertyId}")
  public ResponseEntity viewIndividualPropertyInformation(@PathVariable Integer propertyId) {
    Property property=propertyRepository.findByPropertyId(propertyId);
      return ResponseEntity.status(HttpStatus.OK).body(viewIndividualPropertyService.convertAllThePropertyAttributesToDto(property));
  }





  @GetMapping("/getAllUser")
  public List<User> addUser(){ //Unnecessary method
    return userRepository.findAll();
  }












  @PostMapping(value = "/addProperty")
  public Property addProperty(@RequestBody Property property){ //unnecessary just to add data


    property.setCreatedAt(LocalDateTime.now());

    Category category = property.getCategory();
    boolean categoryExists = categoryRepository.existsByCategory(category.getCategory());
    if(categoryExists)
      category = categoryRepository.findByCategory(category.getCategory());
    else
      category = categoryRepository.findByCategory("Other");
    property.setCategory(category);


    Type type = property.getType();
    boolean typeExists = typeRepository.existsByType(type.getType());
    if(typeExists)
      type = typeRepository.findByType(type.getType());
    else
      type = typeRepository.findByType("Other");
    property.setType(type);


    List<FlatAmenities> flatAmenitiesList = property.getFlatAmenities();
    List<FlatAmenities> flatAmenitiesListNew = new ArrayList<>();
    FlatAmenities flatAmenities ;
    for(int i=0 ; i<flatAmenitiesList.size() ; i++){
      flatAmenities = flatAmenitiesList.get(i);

      if(flatAmenitiesRepository.existsByName(flatAmenities.getName())){
        flatAmenities = flatAmenitiesRepository.findByName(flatAmenities.getName());
      }
      else{
        flatAmenities = flatAmenitiesRepository.findByName("Other");
      }

      flatAmenitiesListNew.add(flatAmenities);
    }
    property.setFlatAmenities(flatAmenitiesListNew);
//property.setSold(property.isSold());
//property.setParkingAvailability(property.getParkingAvailability());

    List<SocietyAmenities> societyAmenitiesList = property.getSocietyAmenities();
    List<SocietyAmenities> societyAmenitiesListNew = new ArrayList<>();
    SocietyAmenities societyAmenities;
    for(int i=0 ; i<societyAmenitiesList.size() ; i++){
      societyAmenities = societyAmenitiesList.get(i);

      if(societyAmenitiesRepository.existsByName(societyAmenities.getName())){
        societyAmenities = societyAmenitiesRepository.findByName(societyAmenities.getName());
      }
      else{
        societyAmenities = societyAmenitiesRepository.findByName("Other");
      }

      societyAmenitiesListNew.add(societyAmenities);
    }
    property.setSocietyAmenities(societyAmenitiesListNew);

    return propertyRepository.save(property);
  }










}
