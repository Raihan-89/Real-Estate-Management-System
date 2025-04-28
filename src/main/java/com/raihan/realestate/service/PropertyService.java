package com.raihan.realestate.service;

import com.raihan.realestate.model.Property;
import com.raihan.realestate.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property createProperty(Property property) {
        // Check if the property already exists (based on title, location, and owner)
        boolean exists = propertyRepository.existsByTitleAndLocationAndOwnerUsername(
                property.getTitle(), property.getLocation(), property.getOwnerUsername()
        );

        if (exists) {
            throw new IllegalArgumentException("This property is already listed by the owner.");
        }

        return propertyRepository.save(property);
    }

    // Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Get a property by ID
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id).orElse(null);
    }

    // Update a property
    public Property updateProperty(String id, Property updatedProperty) {
        Optional<Property> existingProperty = propertyRepository.findById(id);
        if (existingProperty.isPresent()) {
            Property property = existingProperty.get();
            property.setTitle(updatedProperty.getTitle());
            property.setDescription(updatedProperty.getDescription());
            property.setPrice(updatedProperty.getPrice());
            property.setLocation(updatedProperty.getLocation());
            property.setPropertyType(updatedProperty.getPropertyType());
            property.setStatus(updatedProperty.getStatus());
            property.setSize(updatedProperty.getSize());
            property.setBedroom(updatedProperty.getBedroom());
            property.setBathroom(updatedProperty.getBathroom());
            property.setOwnerUsername(updatedProperty.getOwnerUsername());
            property.setLastUpdatedDate(updatedProperty.getLastUpdatedDate());
            property.setImageUrls(updatedProperty.getImageUrls());
            return propertyRepository.save(property);
        }
        return null;
    }

    // Search properties by location, price range, and property type
    public List<Property> searchProperties(String location, Double minPrice, Double maxPrice, String propertyType) {
        // If no filters are provided, return all properties
        if (location == null && minPrice == null && maxPrice == null && propertyType == null) {
            return propertyRepository.findAll();
        }

        // Use query methods with filters based on parameters passed
        if (minPrice != null && maxPrice != null) {
            return propertyRepository.findByLocationAndPriceBetweenAndPropertyType(location, minPrice, maxPrice, propertyType);
        } else if (minPrice != null) {
            return propertyRepository.findByLocationAndPriceGreaterThanEqualAndPropertyType(location, minPrice, propertyType);
        } else if (maxPrice != null) {
            return propertyRepository.findByLocationAndPriceLessThanEqualAndPropertyType(location, maxPrice, propertyType);
        } else if (location != null) {
            return propertyRepository.findByLocationAndPropertyType(location, propertyType);
        } else if (propertyType != null) {
            return propertyRepository.findByPropertyType(propertyType);
        }

        return propertyRepository.findAll();
    }

    // Delete a property
    public boolean deleteProperty(String id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public long countProperties() {
        return propertyRepository.count(); // Count all properties in the database
    }
    public long countByPostedDate(Date date) {
        return propertyRepository.countByPostedDate(date);
    }
    public List<Property> getSoldProperties() {
        return propertyRepository.findByStatus("Sold");
    }
}
