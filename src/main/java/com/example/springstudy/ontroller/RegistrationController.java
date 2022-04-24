package com.example.springstudy.ontroller;

import com.example.springstudy.domain.Role;
import com.example.springstudy.domain.User;
import com.example.springstudy.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        if (user.getPassword().length() == 0 || user.getUsername().length() == 0) {
            model.put("message", "Fill in all the fields");
            return "registration";
        }

        if (user.getUsername().equals(user.getPassword())) {
            model.put("message", "Password and username match!");
            return "registration";
        }

        if (user.getUsername().length() < 3) {
            model.put("message", "Username is too short!");
            return "registration";
        }

        if (user.getPassword().length() < 3) {
            model.put("message", "Password is too short!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}