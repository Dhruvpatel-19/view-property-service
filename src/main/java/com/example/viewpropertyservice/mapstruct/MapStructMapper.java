package com.example.viewpropertyservice.mapstruct;

import com.example.viewpropertyservice.dto.AllPropertyDTO;
import com.example.viewpropertyservice.dto.PropertyDTO;
import com.example.viewpropertyservice.entity.Property;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    AllPropertyDTO propertyToAllPropertyDto(Property property);
    PropertyDTO propertyToPropertyDto(Property property);
}
