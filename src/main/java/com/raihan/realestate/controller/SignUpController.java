package com.raihan.realestate.controller;

import com.raihan.realestate.dto.SignInDto;
import com.raihan.realestate.dto.SignUpDto;
import com.raihan.realestate.model.Name;
import com.raihan.realestate.model.User;
import com.raihan.realestate.model.UserType;
import com.raihan.realestate.repository.UserExistenceCheck;
import com.raihan.realestate.repository.UserRepository;
import com.raihan.realestate.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class SignUpController implements UserExistenceCheck {

    private final UserService userService;
    private final UserRepository userRepository;

    public SignUpController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("dto", new SignUpDto(false));
        return "signup";
    }
    @PostMapping("/signUp")
    public String signUp(@ModelAttribute SignUpDto dto, Model model) {
        User user = new User();
        user.setName(new Name(dto.getFirstName(), dto.getLastName()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        if(dto.getUserType().equals("Seller")){
            user.setUserType(UserType.SELLER);
        } else if (dto.getUserType().equals("Buyer")) {
            user.setUserType(UserType.BUYER);
        }

        boolean isPresent = checkUserExistence(user);
        if(!isPresent) {
            userService.save(user);
            SignInDto signInDto = new SignInDto();
            signInDto.setSignUpSuccess(true);
            model.addAttribute("dto", signInDto);
            return "redirect:/signIn";
        }
        else {
            model.addAttribute("dto", new SignUpDto(true));
            return "redirect:/signUp";
        }
//        userService.save(user);

//        SignInDto signInDto = new SignInDto();
//        signInDto.setSignUpSuccess(true);
//        model.addAttribute("dto", signInDto);
//        return "signin";
    }

    @Override
    public boolean checkUserExistence(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        Optional<User> userOptional2 = userRepository.findByEmail(user.getEmail());

        return userOptional2.isPresent() && userOptional.isPresent();
    }
}
