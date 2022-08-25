package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.service.ViewIndividualPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/viewPropertyService")
public class ViewIndividualPropertyInformationController {

  @Autowired ViewIndividualPropertyService viewIndividualPropertyService;
  @Autowired PropertyRepository propertyRepository;



  @GetMapping("/getProperty/{propertyId}")
  public ResponseEntity viewIndividualPropertyInformation(@PathVariable Integer propertyId) {
    Property property=propertyRepository.findByPropertyId(propertyId);
      return ResponseEntity.status(HttpStatus.OK).body(viewIndividualPropertyService.convertAllThePropertyAttributesToDto(property));
  }


}
