package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.ViewPropertyInformationDto;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.service.ViewIndividualPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewIndividualPropertyInformationController {

  @Autowired ViewIndividualPropertyService viewIndividualPropertyService;
  @Autowired PropertyRepository propertyRepository;
  @GetMapping("/{propertyId}")
  public ViewPropertyInformationDto viewIndividualPropertyInformation(@PathVariable Integer propertyId) {
    Property property=propertyRepository.findByPropertyId(propertyId);
    return viewIndividualPropertyService.convertAllThePropertyAttributesToDto(property);

  }






}
