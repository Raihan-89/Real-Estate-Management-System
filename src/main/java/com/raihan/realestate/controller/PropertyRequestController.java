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
public class PropertyRequestController {
    private final UserService userService;
    private final PropertyRequestService propertyRequestService;
    private final PropertyService propertyService;

    public PropertyRequestController(UserService userService, PropertyRequestService propertyRequestService, PropertyService propertyService) {
        this.userService = userService;
        this.propertyRequestService = propertyRequestService;
        this.propertyService = propertyService;
    }
    @GetMapping("/property-request-list")
    public String listProperties(Model model) {
        List<PropertyRequest> propertyRequests = propertyRequestService.getAllProperties();
        model.addAttribute("propertyRequests", propertyRequests);
        return "property-request-list";
    }

    @GetMapping("/property-request-list/new")
    public String showCreatePropertyForm(Model model) {
        List<User> userList = userService.getAll();

        model.addAttribute("users", userList);
        model.addAttribute("propertyRequest", new PropertyRequest());
        return "property-request-create";
    }

    @PostMapping("/property-request-list/save")
    public String createProperty(@ModelAttribute PropertyRequest propertyRequest, @RequestParam("images") MultipartFile[] images, Model model) {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                String uploadDir = "src/main/resources/static/uploads/";

                imagePaths.add("/uploads/" + fileName);
            }
        }

        propertyRequest.setImageUrls(imagePaths);
        propertyRequest.setPostedDate(Date.valueOf(LocalDate.now()));
        propertyRequestService.createProperty(propertyRequest);
        model.addAttribute("propertyRequest", propertyRequest);
        return "redirect:/property-request-list";
    }

    @PostMapping("/property-request-list/approve/{id}")
    public String approvePropertyRequest(@PathVariable String id, Model model) {
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);

        Property property = new Property();
        property.setTitle(propertyRequest.getTitle());
        property.setDescription(propertyRequest.getDescription());
        property.setPrice(propertyRequest.getPrice());
        property.setLocation(propertyRequest.getLocation());
        property.setPropertyType(propertyRequest.getPropertyType());
        property.setStatus("Available");
        property.setSize(propertyRequest.getSize());
        property.setBedroom(propertyRequest.getBedroom());
        property.setBathroom(propertyRequest.getBathroom());
        property.setOwnerUsername(propertyRequest.getOwnerUsername());
        property.setPostedDate(Date.valueOf(LocalDate.now()));
        property.setImageUrls(propertyRequest.getImageUrls());

        propertyService.createProperty(property);
        propertyRequestService.deleteProperty(id);
        List<PropertyRequest> propertyRequests = propertyRequestService.getAllProperties();
        model.addAttribute("propertyRequests", propertyRequests);
        return "redirect:/property-request-list";
    }

    @GetMapping("/property-request-list/view/{id}")
    public String viewPropertyForm(@PathVariable String id, Model model) {
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);
        if (propertyRequest == null) {
            model.addAttribute("error", "Property not found!");
            return "error";
        }
        return "redirect:/property-list/view/"+id;
    }

    @GetMapping("/property-request-list/edit/{id}")
    public String showEditPropertyForm(@PathVariable String id, Model model) {
        PropertyRequest propertyRequest = propertyRequestService.getPropertyById(id);
        if (propertyRequest == null) {
            model.addAttribute("error", "Property not found!");
            return "error";
        }
        return "redirect:/property-list/edit/" + id;
    }

    @DeleteMapping("/property-request-list/delete/{id}")
    public String deleteProperty(@PathVariable String id, Model model) {
        propertyRequestService.deleteProperty(id);
        model.addAttribute("message", "Property deleted successfully!");
        return "redirect:/property-request-list";
    }

    @GetMapping("/property-request-list/search")
    public String searchProperties(@RequestParam(required = false) String location,
                                   @RequestParam(required = false) Double minPrice,
                                   @RequestParam(required = false) Double maxPrice,
                                   @RequestParam(required = false) String propertyType,
                                   Model model) {
        List<PropertyRequest> propertyRequests = propertyRequestService.searchProperties(location, minPrice, maxPrice, propertyType);
        model.addAttribute("propertyRequests", propertyRequests);
        return "property-request-list";
    }
}
