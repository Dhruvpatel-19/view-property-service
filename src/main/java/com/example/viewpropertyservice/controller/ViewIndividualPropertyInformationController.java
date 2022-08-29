package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.entity.*;
import com.example.viewpropertyservice.repository.*;
import com.example.viewpropertyservice.service.ViewIndividualPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
