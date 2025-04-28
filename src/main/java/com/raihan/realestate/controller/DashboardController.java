package com.raihan.realestate.controller;

import com.raihan.realestate.dto.SignInDto;
import com.raihan.realestate.model.Property;
import com.raihan.realestate.model.User;
import com.raihan.realestate.service.PropertyRequestService;
import com.raihan.realestate.service.PropertyService;
import com.raihan.realestate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    private final UserService userService;
    private final PropertyService propertyService;
    private final PropertyRequestService propertyRequestService;

    public DashboardController(UserService userService, PropertyService propertyService, PropertyRequestService propertyRequestService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.propertyRequestService = propertyRequestService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user"); // ✅ Retrieve from session
        if(user == null) {
            return "redirect:/signIn";
        }
        long propertyCount = propertyService.countProperties();
        long userCount = userService.countUsers();
        long propertyRequestCount = propertyRequestService.countPropertyRequests();
        long todayPropertyListCount = propertyService.countByPostedDate(Date.valueOf(LocalDate.now()));
        List<Property> soldPropertyList = propertyService.getSoldProperties();
        model.addAttribute("soldPropertyList", soldPropertyList);
        model.addAttribute("propertyCount", propertyCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("propertyRequestCount", propertyRequestCount);
        model.addAttribute("todayPropertyListCount", todayPropertyListCount);
        model.addAttribute("user", user);
        return "dashboard";
    }
    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("dto", new SignInDto());
        return "admin-dashboard";
    }
}
