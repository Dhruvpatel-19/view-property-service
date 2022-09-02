package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.service.ViewPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/viewPropertyService")
public class ViewPropertyController {

    @Autowired
    private ViewPropertyService viewPropertyService;

    @Operation(summary = "Get Property By ID API",description = "This property is used to get property using their user ID", tags = {"ViewPropertyController"})
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Property found successfully"),
      @ApiResponse(responseCode = "400",description = "Bad Request"),
      @ApiResponse(responseCode = "404",description = "Property not found"),
      @ApiResponse(responseCode = "403",description = "Forbidden")
    })
    @GetMapping("/getProperty/{propertyId}")
    public PropertyDTO getPropertyById(@PathVariable("propertyId") int propertyId) {
        return viewPropertyService.getPropertyById(propertyId);
    }
    @Operation(summary = "Post Property To Users Favourite",description = "This property will post users favourite", tags = {"ViewPropertyController"})
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Property posted successfully"),
      @ApiResponse(responseCode = "400",description = "Bad Request"),
      @ApiResponse(responseCode = "404",description = "Property not found"),
      @ApiResponse(responseCode = "403",description = "Forbidden")
    })
    @GetMapping("/addToFavourite/{propertyId}")
    public String addFavourite(HttpServletRequest request,@PathVariable("propertyId")int id) throws Exception {
        return viewPropertyService.addToFavourite(request , id);
    }

    @Operation(summary = "Remove Property From Favourite",description = "This method is used to remove property from favourite.", tags = {"ViewPropertyController"})
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Property removed successfully"),
      @ApiResponse(responseCode = "400",description = "Bad Request"),
      @ApiResponse(responseCode = "404",description = "Property not found"),
      @ApiResponse(responseCode = "403",description = "Forbidden")
    })
    @GetMapping("/removeFromFavourite/{propertyId}")
    public String removeFromFavourite(HttpServletRequest request ,@PathVariable("propertyId") int id) throws Exception {
        return viewPropertyService.removeFromFavourite(request , id);
    }

    @Operation(summary = "Get All Favourite Property From Favourite",description = "This method is used to get all the favourite property of the user", tags = {"ViewPropertyController"})
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reteived all property successfully"),
      @ApiResponse(responseCode = "400",description = "Bad Request"),
      @ApiResponse(responseCode = "404",description = "Property not found"),
      @ApiResponse(responseCode = "403",description = "Forbidden")
    })
    @GetMapping("/getAllFavourite")
    public List<AllPropertyDTO> getAllFavourites(HttpServletRequest request) throws Exception {
        return viewPropertyService.getAllFavourite(request);
    }
}
