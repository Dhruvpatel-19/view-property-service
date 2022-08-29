package com.example.viewpropertyservice.repository;

import com.example.viewpropertyservice.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

}
