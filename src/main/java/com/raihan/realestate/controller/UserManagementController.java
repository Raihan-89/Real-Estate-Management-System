package com.raihan.realestate.controller;

import com.raihan.realestate.model.Name;
import com.raihan.realestate.model.User;
import com.raihan.realestate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-management")
    public String userManagement(Model model) {
        List<User> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return "user-management";
    }
    @GetMapping("/users/view/{username}")
    public ResponseEntity<User> viewUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/users/edit/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/users/edit")
    public String editUser(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("username") String username,
                           RedirectAttributes redirectAttributes) {

        User optionalUser = userService.findByUsername(username);
        Name name = userService.findByUsername(username).getName();

        if (optionalUser != null) {
            User user = optionalUser;
            name.setFirstName(firstName);
            name.setLastName(lastName);
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);

            userService.save(user);

            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found!");
        }
        return "redirect:/user-management";
    }
    @DeleteMapping("/users/delete/{username}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

}
