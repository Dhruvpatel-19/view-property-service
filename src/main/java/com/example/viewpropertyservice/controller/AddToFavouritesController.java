package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.User;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.repository.FavouritesRepository;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/viewPropertyService")
public class AddToFavouritesController { //here we assume that we have JWT token with us, using which we extract username so that we can add favourite property

@Autowired JwtUtil jwtUtil;
@Autowired UserRepository userRepository;

@Autowired PropertyRepository propertyRepository;

@Autowired FavouritesRepository favouritesRepository;
/*

@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id
												  ,@RequestBody Employee employee){
		return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	}

 */



  @PutMapping(value="/addToFavourites/{propertyId}")
  public ResponseEntity addToFavourites(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("propertyId") int propertyId) throws Exception {
    //One user can have only one fav id and one fav id can have list of properties that are favourite to that particular user,
    //we will be able to retrieve  user id i.e. email, we'd be able to retrive the fav id of the user from  user entity and in
    // that fav id we will append the property that the user likes.
    String requestTokenHeader = httpServletRequest.getHeader("Authorization");
    String jwtToken = null;
    String email = null;
    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
        email = jwtUtil.extractUsername(requestTokenHeader.substring(7));
    }

      User user = userRepository.findByEmail(email); //get the email from token
      Favourites favourites=user.getFavourites();    //get the favourite of the user that has favourite ID of the user
      favourites.setCreatedAt(LocalDateTime.now());  //set user's - favourites created at.
      Property property = propertyRepository.findByPropertyId(propertyId);  // using property ID fetch the property that the user likees
      List<Property> propertyList=favourites.getPropertyList();  //get the list of properties that user has already liked
      propertyList.add(property); // add the currently liked property that user has liked to the list of already liked property
      favourites.setPropertyList(propertyList); //set it to the user's list of property in favourites.
      //old code when the user has one to many mapping with favourite and favourite has one to one mapping with property that user likes.
      favouritesRepository.save(favourites);//saved to database
//      userRepository.save(user); we dont need to save user as we havent updated any data in usertable only the favourites and favid propid table is updated.
    return  new ResponseEntity("property added to particular user's favourites list" , HttpStatus.OK);
  }




//  @DeleteMapping(value="/deleteFromFavourites/{propertyId}")
//  public ResponseEntity deleteFromFavourites(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("propertyId") int propertyId) throws Exception {
//    //One user can have only one fav id and one fav id can have list of properties that are favourite to that particular user,
//    //we will be able to retrieve  user id i.e. email, we'd be able to retrive the fav id of the user from  user entity and in
//    // that fav id we will append the property that the user likes.
//    String requestTokenHeader = httpServletRequest.getHeader("Authorization");
//    String jwtToken = null;
//    String email = null;
//    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
//      email = jwtUtil.extractUsername(requestTokenHeader.substring(7));
//    }
//
//    User user = userRepository.findByEmail(email); //get the email from token
//    Favourites favourites=user.getFavourites();    //get the favourite of the user that has favourite ID of the user
//    favourites.setCreatedAt(LocalDateTime.now());  //set user's - favourites created at.
//    List<Property> propertyList=favourites.getPropertyList();  //get the list of properties that user has already liked
//
//    propertyList.remove(PropertyRepository.findByPropertyId(propertyId));
//    favouritesRepository.deleteById(propertyId);//saved to database
//    userRepository.save(user);
//
//
//
//    return  new ResponseEntity("property added to particular users favourites list " , HttpStatus.OK);
//  }








 /*

          {
        "userId": 1,
        "firstName": "Jay",
        "lastName": "Rathod",
        "email": "jay@gmail.com",
        "mobileNumber": "915121",
        "password": "1234",
        "createdAt": "2022-08-26T12:01:57.69383",
        "propertyList": [],
        "favourites": []
    }

 */

}
