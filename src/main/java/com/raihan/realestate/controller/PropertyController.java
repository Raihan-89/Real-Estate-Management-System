package com.raihan.realestate.controller;

import com.raihan.realestate.model.Property;
import com.raihan.realestate.model.PropertyRequest;
import com.raihan.realestate.model.User;
import com.raihan.realestate.service.PropertyRequestService;
import com.raihan.realestate.service.PropertyService;
import com.raihan.realestate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class PropertyController {
    private final UserService userService;
    private final PropertyService propertyService;
    private final PropertyRequestService propertyRequestService;

    public PropertyController(UserService userService, PropertyService propertyService, PropertyRequestService propertyRequestService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.propertyRequestService = propertyRequestService;
    }

    @GetMapping("/property-list")
    public String listProperties(Model model) {
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties", properties);
        return "property-list";
    }

    @GetMapping("/property-list/new")
    public String showCreatePropertyForm(Model model) {
        List<User> userList = userService.getAll();

        model.addAttribute("users", userList);
        model.addAttribute("property", new Property());
        return "property-create";
    }

    @PostMapping("/property-list/save")
    public String createProperty(@ModelAttribute Property property, @RequestParam("images") MultipartFile[] images, Model model) {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                String uploadDir = "src/main/resources/static/uploads/";

                imagePaths.add("/uploads/" + fileName);
            }
        }

        property.setImageUrls(imagePaths);
        property.setPostedDate(Date.valueOf(LocalDate.now()));
        propertyService.createProperty(property);
        model.addAttribute("property", property);
        return "redirect:/property-list";
    }

    @GetMapping("/property-list/view/{id}")
    public String viewPropertyForm(@PathVariable String id, Model model) {
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);
        if(propertyRequest != null) {
            model.addAttribute("property", propertyRequest);
            model.addAttribute("mode", "view");
            return "property-edit";
        }
        else{
            Property property = propertyService.getPropertyById(id);
            if (property == null) {
                model.addAttribute("error", "Property not found!");
                return "error";
            }
            model.addAttribute("property", property);
            model.addAttribute("mode", "view");
            return "property-edit";
        }
    }

    @GetMapping("/property-list/edit/{id}")
    public String showEditPropertyForm(@PathVariable String id, Model model) {
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);
        if(propertyRequest != null) {
            List<User> userList = userService.getAll();

            model.addAttribute("users", userList);
            model.addAttribute("property", propertyRequest);
            model.addAttribute("mode", "edit");
            return "property-edit";
        }
        else{
            Property property = propertyService.getPropertyById(id);
            if (property == null) {
                model.addAttribute("error", "Property not found!");
                return "error";
            }
            List<User> userList = userService.getAll();

            model.addAttribute("users", userList);
            model.addAttribute("property", property);
            model.addAttribute("mode", "edit");
            return "property-edit";
        }
    }

    @PostMapping("/property-list/edit/{id}")
    public String updateProperty(@PathVariable String id, @ModelAttribute Property property,@RequestParam("images") MultipartFile[] images, Model model) {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                String uploadDir = "src/main/resources/static/uploads/";

                imagePaths.add("/uploads/" + fileName);
            }
        }
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);
        if(propertyRequest != null) {
            propertyRequest.setTitle(property.getTitle());
            propertyRequest.setDescription(property.getDescription());
            propertyRequest.setPropertyType(property.getPropertyType());
            propertyRequest.setBathroom(property.getBathroom());
            propertyRequest.setBedroom(property.getBedroom());
            propertyRequest.setLocation(property.getLocation());
            propertyRequest.setOwnerUsername(property.getOwnerUsername());
            propertyRequest.setPrice(property.getPrice());
            propertyRequest.setSize(property.getSize());
            propertyRequest.setStatus(property.getStatus());
            propertyRequest.setImageUrls(imagePaths);
            propertyRequest.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
            propertyRequestService.updateProperty(id, propertyRequest);
            model.addAttribute("message", "Property updated successfully!");
            return "redirect:/property-request-list";
        }
        else {
            property.setImageUrls(imagePaths);
            property.setLastUpdatedDate(Date.valueOf(LocalDate.now()));
            propertyService.updateProperty(id, property);
            model.addAttribute("message", "Property updated successfully!");
            return "redirect:/property-list";
        }
    }

    @DeleteMapping("/property-list/delete/{id}")
    public String deleteProperty(@PathVariable String id, Model model) {
        propertyService.deleteProperty(id);
        model.addAttribute("message", "Property deleted successfully!");
        return "redirect:/property-list";
    }

    @GetMapping("/property-list/search")
    public String searchProperties(@RequestParam(required = false) String location,
                                   @RequestParam(required = false) Double minPrice,
                                   @RequestParam(required = false) Double maxPrice,
                                   @RequestParam(required = false) String propertyType,
                                   Model model) {
        List<Property> properties = propertyService.searchProperties(location, minPrice, maxPrice, propertyType);
        model.addAttribute("properties", properties);
        return "property-list";
    }
}
