package com.raihan.realestate.controller;

import com.raihan.realestate.model.Name;
import com.raihan.realestate.model.User;
import com.raihan.realestate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MyProfileController {

    private final UserService userService;

    public MyProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-profile")
    public String myProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "my-profile";
    }
    @GetMapping("/my-profile/edit/{username}")
    public String editProfile(Model model, @PathVariable String username) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return "redirect:/my-profile";
        }
        model.addAttribute("user", user);
        return "my-profile-edit";
    }
    @PostMapping("/my-profile/edit/")
    public String editProfile(Model model, @RequestParam("username") String username, @ModelAttribute User user) {
        User user1 = userService.findByUsername(username);
        if(user1 != null) {
            user1.setLocation(user.getLocation());
            user1.setPhone(user.getPhone());
            userService.save(user1);
            model.addAttribute("user", user);
            return "redirect:/my-profile";
        }
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/my-profile";

    }
}
