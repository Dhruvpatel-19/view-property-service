package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.dto.FavouriteDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.service.ViewPropertyService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get Property By Id",description = "This property is used to get property by Id", tags = {"ViewPropertyController"})
    @GetMapping("/getProperty/{propertyId}")
    public PropertyDTO getPropertyById(@PathVariable("propertyId") int propertyId) {
        return viewPropertyService.getPropertyById(propertyId);
    }

    @Operation(summary = "Add Property To Users Favourite List",description = "This property will be added to users favourite", tags = {"ViewPropertyController"})
    @GetMapping("/addToFavourite/{propertyId}")
    public FavouriteDTO addFavourite(HttpServletRequest request, @PathVariable("propertyId")int id){
        return viewPropertyService.addToFavourite(request , id);
    }

    @Operation(summary = "Remove Property From User Favourite List",description = "This method is used to remove property from favourite.", tags = {"ViewPropertyController"})
    @GetMapping("/removeFromFavourite/{propertyId}")
    public FavouriteDTO removeFromFavourite(HttpServletRequest request ,@PathVariable("propertyId") int id){
        return viewPropertyService.removeFromFavourite(request , id);
    }

    @Operation(summary = "Get All Favourite Property From User Favourite List",description = "This method is used to get all the favourite property of the user", tags = {"ViewPropertyController"})
    @GetMapping("/getAllFavourite")
    public List<AllPropertyDTO> getAllFavourites(HttpServletRequest request){
        return viewPropertyService.getAllFavourite(request);
    }
}
