package com.example.viewpropertyservice.controller;

import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.User;
import com.example.viewpropertyservice.jwt.JwtUtil;
import com.example.viewpropertyservice.repository.PropertyRepository;
import com.example.viewpropertyservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/viewPropertyService")
public class AddToFavouritesController { //here we assume that we have JWT token with us, using which we extract username so that we can add favourite property

@Autowired JwtUtil jwtUtil;
@Autowired UserRepository userRepository;

@Autowired PropertyRepository propertyRepository;


/*

@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id
												  ,@RequestBody Employee employee){
		return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	}

 */



  @PutMapping(value="/addToFavourites/{propertyId}")
  public ResponseEntity<User> addToFavourites(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("propertyId") int propertyId) throws Exception {

    String requestTokenHeader = httpServletRequest.getHeader("Authorization");
    String jwtToken = null;
    String email = null;
    if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
        email = jwtUtil.extractUsername(requestTokenHeader.substring(7));
    }

      User user = userRepository.findByEmail(email); //get the email from token
      Favourites favourites =new Favourites();
      favourites.setCreatedAt(LocalDateTime.now());
      Property property=propertyRepository.findByPropertyId(propertyId);
      favourites.setProperty(property);
      List<Favourites> favList= user.getFavourites(); //get the list of favourites entity.
      favList.add(favourites);// add the newly created favourite object to the favourites list
      user.setFavourites(favList);
    return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
  }



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
