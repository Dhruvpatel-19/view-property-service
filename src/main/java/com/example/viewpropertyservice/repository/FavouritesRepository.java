package com.example.viewpropertyservice.repository;

import com.example.viewpropertyservice.entity.Favourites;
import com.example.viewpropertyservice.entity.Property;
import com.example.viewpropertyservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

    void deleteByUserAndProperty(User user, Property property);

    Favourites findByUserAndProperty(User user, Property property);

    boolean existsByUserAndProperty(User user, Property property);
}
