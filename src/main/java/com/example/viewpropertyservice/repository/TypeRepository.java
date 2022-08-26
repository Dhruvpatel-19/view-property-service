package com.example.viewpropertyservice.repository;

import com.example.viewpropertyservice.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    boolean existsByType(String type);

    Type findByType(String type);
}
