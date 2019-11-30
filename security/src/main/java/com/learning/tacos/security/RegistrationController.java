package com.learning.tacos.security;

import com.learning.tacos.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }
        userRepository.save(form.toUser(passwordEncoder));
        log.info("User registered {}", form.getUsername());
        return "redirect:/login";
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }
}
