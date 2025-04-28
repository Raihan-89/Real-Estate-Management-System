package com.raihan.realestate.controller;

import com.raihan.realestate.dto.SignInDto;
import com.raihan.realestate.dto.SignUpDto;
import com.raihan.realestate.model.Name;
import com.raihan.realestate.model.User;
import com.raihan.realestate.model.UserType;
import com.raihan.realestate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SignInController {
    private final UserService userService;

    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signIn")
    public String signIn(Model model) {

        model.addAttribute("dto", new SignInDto(false));
        return "signin";
    }

    @PostMapping("/signIn")
    public String signUp(@ModelAttribute SignInDto dto, Model model, HttpSession session) {
        boolean isValid = userService.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(isValid) {
            model.addAttribute("dto", new SignInDto(false));
            User user = userService.findByEmail(dto.getEmail());
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }else{
            model.addAttribute("dto", new SignInDto(true));
            return "signin";
        }
    }
}
