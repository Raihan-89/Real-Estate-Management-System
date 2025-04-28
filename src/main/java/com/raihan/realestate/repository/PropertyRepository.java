package com.raihan.realestate.repository;

import com.raihan.realestate.model.Property;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
    boolean existsByTitleAndLocationAndOwnerUsername(String title, String location, String ownerUsername);
    List<Property> findByLocationAndPriceBetweenAndPropertyType(String location, Double minPrice, Double maxPrice, String propertyType);
    List<Property> findByLocationAndPriceGreaterThanEqualAndPropertyType(String location, Double minPrice, String propertyType);
    List<Property> findByLocationAndPriceLessThanEqualAndPropertyType(String location, Double maxPrice, String propertyType);
    List<Property> findByLocationAndPropertyType(String location, String propertyType);
    List<Property> findByPropertyType(String propertyType);
    List<Property> findByStatus(String status);
    long countByPostedDate(Date postedDate);
}
