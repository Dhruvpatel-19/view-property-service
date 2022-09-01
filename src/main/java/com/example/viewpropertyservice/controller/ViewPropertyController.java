package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.service.ViewPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/viewPropertyService")
public class ViewPropertyController {

    @Autowired
    private ViewPropertyService viewPropertyService;

    @GetMapping("/getProperty/{propertyId}")
    public Property getPropertyById(@PathVariable("propertyId") int propertyId) {
        return viewPropertyService.getPropertyById(propertyId);
    }

    @GetMapping("/addToFavourite/{propertyId}")
    public String addFavourite(HttpServletRequest request,@PathVariable("propertyId")int id) throws Exception {
        return viewPropertyService.addToFavourite(request , id);
    }

    @GetMapping("/removeFromFavourite/{propertyId}")
    public String removeFromFavourite(HttpServletRequest request ,@PathVariable("propertyId") int id) throws Exception {
        return viewPropertyService.removeFromFavourite(request , id);
    }
    @GetMapping("/getAllFavourite")
    public List<AllPropertyDTO> getAllFavourites(HttpServletRequest request) throws Exception {
        return viewPropertyService.getAllFavourite(request);
    }
}
