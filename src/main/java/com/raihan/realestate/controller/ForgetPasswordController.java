//package com.raihan.realestate.controller;
//
//import com.raihan.realestate.model.User;
//import com.raihan.realestate.service.UserService;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Random;
//
//@Controller
//public class ForgetPasswordController {
//    private final UserService userService;
//    private final JavaMailSender mailSender;
//
//    public ForgetPasswordController(UserService userService, JavaMailSender mailSender) {
//        this.userService = userService;
//        this.mailSender = mailSender;
//    }
//
//    private Map<String, String> otpStorage = new HashMap<>(); // Store OTPs temporarily
//
//    // 1️⃣ Show the email entry page
//    @GetMapping("/forget-password")
//    public String showForgotPasswordPage() {
//        return "forget-password";
//    }
//
//    // 2️⃣ Handle email submission, generate and send OTP
//    @PostMapping("/forget-password")
//    public String sendOTP(@RequestParam("email") String email, Model model) {
//        User user = userService.findByEmail(email);
//
//        if (user == null) {
//            model.addAttribute("error", "Email not found!");
//            return "forget-password";
//        }
//
//        // Generate 4-digit OTP
//        String otp = String.format("%04d", new Random().nextInt(10000));
//        otpStorage.put(email, otp);
//
//        // Send email with OTP
//        sendEmail(email, "Password Reset OTP", "Your OTP is: " + otp);
//
//        // Pass email to the next step
//        model.addAttribute("email", email);
//        return "verify-otp";
//    }
//
//    // 3️⃣ Verify the OTP
//    @PostMapping("/forget-password/verify")
//    public String verifyOTP(@RequestParam("email") String email,
//                            @RequestParam("otp") String otp, Model model) {
//        if (otp.equals(otpStorage.get(email))) {
//            model.addAttribute("email", email);
//            return "reset-password"; // Show new password form
//        } else {
//            model.addAttribute("error", "Invalid OTP! Try again.");
//            return "verify-otp";
//        }
//    }
//
//    // 4️⃣ Update password in database
//    @PostMapping("/forget-password/reset")
//    public String resetPassword(@RequestParam("email") String email,
//                                @RequestParam("password") String password) {
//        userService.updatePassword(email, password);
//        otpStorage.remove(email); // Remove OTP after success
//        return "redirect:/login?resetSuccess";
//    }
//
//    // ✅ Helper method to send email
//    private void sendEmail(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//    }
//}
