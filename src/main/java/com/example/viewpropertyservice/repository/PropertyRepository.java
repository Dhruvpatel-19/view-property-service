package com.example.viewpropertyservice.repository;

import com.example.viewpropertyservice.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

  Property findByPropertyId(Integer propertyId);
}



