package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.service.ViewPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/viewPropertyService")
public class ViewPropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ViewPropertyService viewPropertyService;

    @GetMapping("/getProperty/{propertyId}")
    public ResponseEntity viewIndividualPropertyInformation(@PathVariable int propertyId) {
        Property property=propertyRepository.findByPropertyId(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(viewPropertyService.convertAllThePropertyAttributesToDto(property));
    }

    @GetMapping("/addToFavourite/{propertyId}")
    public String addFavourite(HttpServletRequest request,@PathVariable("propertyId")int id) throws Exception {
        return viewPropertyService.addToFavourite(request , id);
    }
}
