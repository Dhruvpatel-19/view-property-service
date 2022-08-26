package com.example.viewpropertyservice.repository;


import com.example.viewpropertyservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

}
