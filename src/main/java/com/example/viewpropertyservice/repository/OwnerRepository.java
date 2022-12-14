package com.example.viewpropertyservice.repository;

import com.example.viewpropertyservice.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner , Integer> {
    Owner findByEmail(String email);
}
