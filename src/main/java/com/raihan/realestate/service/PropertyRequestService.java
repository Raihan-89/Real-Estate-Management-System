package com.raihan.realestate.service;

import com.raihan.realestate.model.Property;
import com.raihan.realestate.model.PropertyRequest;
import com.raihan.realestate.repository.PropertyRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyRequestService {
    private final PropertyRequestRepository propertyRequestRepository;

    public PropertyRequestService(PropertyRequestRepository propertyRequestRepository) {
        this.propertyRequestRepository = propertyRequestRepository;
    }
    public PropertyRequest createProperty(PropertyRequest propertyRequest) {
        boolean exists = propertyRequestRepository.existsByTitleAndLocationAndOwnerUsername(
                propertyRequest.getTitle(), propertyRequest.getLocation(), propertyRequest.getOwnerUsername()
        );

        if (exists) {
            throw new IllegalArgumentException("This property is already listed by the owner.");
        }

        return propertyRequestRepository.save(propertyRequest);
    }

    // Get all properties
    public List<PropertyRequest> getAllProperties() {
        return propertyRequestRepository.findAll();
    }

    // Get a property by ID
    public PropertyRequest getPropertyById(String id) {
        return propertyRequestRepository.findById(id).orElse(null);
    }

    // Update a property
    public PropertyRequest updateProperty(String id, PropertyRequest updatedProperty) {
        Optional<PropertyRequest> existingProperty = propertyRequestRepository.findById(id);
        if (existingProperty.isPresent()) {
            PropertyRequest property = existingProperty.get();
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
            return propertyRequestRepository.save(property);
        }
        return null;
    }

    // Search properties by location, price range, and property type
    public List<PropertyRequest> searchProperties(String location, Double minPrice, Double maxPrice, String propertyType) {
        // If no filters are provided, return all properties
        if (location == null && minPrice == null && maxPrice == null && propertyType == null) {
            return propertyRequestRepository.findAll();
        }

        // Use query methods with filters based on parameters passed
        if (minPrice != null && maxPrice != null) {
            return propertyRequestRepository.findByLocationAndPriceBetweenAndPropertyType(location, minPrice, maxPrice, propertyType);
        } else if (minPrice != null) {
            return propertyRequestRepository.findByLocationAndPriceGreaterThanEqualAndPropertyType(location, minPrice, propertyType);
        } else if (maxPrice != null) {
            return propertyRequestRepository.findByLocationAndPriceLessThanEqualAndPropertyType(location, maxPrice, propertyType);
        } else if (location != null) {
            return propertyRequestRepository.findByLocationAndPropertyType(location, propertyType);
        } else if (propertyType != null) {
            return propertyRequestRepository.findByPropertyType(propertyType);
        }

        return propertyRequestRepository.findAll();
    }

    // Delete a property
    public boolean deleteProperty(String id) {
        if (propertyRequestRepository.existsById(id)) {
            propertyRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long countPropertyRequests() {
        return propertyRequestRepository.count();
    }
}
