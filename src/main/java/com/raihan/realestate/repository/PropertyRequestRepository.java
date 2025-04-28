package com.raihan.realestate.repository;

import com.raihan.realestate.model.Property;
import com.raihan.realestate.model.PropertyRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRequestRepository extends MongoRepository<PropertyRequest, String> {
    boolean existsByTitleAndLocationAndOwnerUsername(String title, String location, String ownerUsername);
    // Find properties by location and price range
    List<PropertyRequest> findByLocationAndPriceBetweenAndPropertyType(String location, Double minPrice, Double maxPrice, String propertyType);

    // Find properties by location and price (greater than or equal)
    List<PropertyRequest> findByLocationAndPriceGreaterThanEqualAndPropertyType(String location, Double minPrice, String propertyType);

    // Find properties by location and price (less than or equal)
    List<PropertyRequest> findByLocationAndPriceLessThanEqualAndPropertyType(String location, Double maxPrice, String propertyType);

    // Find properties by location and property type
    List<PropertyRequest> findByLocationAndPropertyType(String location, String propertyType);

    // Find properties by property type
    List<PropertyRequest> findByPropertyType(String propertyType);
}
