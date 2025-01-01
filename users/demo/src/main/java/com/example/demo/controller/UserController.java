package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegistrationDTO;
import com.example.demo.dto.UpdateDTO;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor()
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/healthCheck")
    public String test(){
        return "I am healthy";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        Boolean authenticated = userService.Login(loginDTO);
        if (authenticated) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/register")
    public String Register(@RequestBody RegistrationDTO registrationDTO) {
        userService.addUser(registrationDTO);
        return "success";
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<User> getUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDetails(username));
    }

    @PutMapping("/profile")
    public String updateUser(@RequestBody UpdateDTO updateDTO) {
        Boolean authenticated = userService.UpdateUser(updateDTO);
        if (authenticated) {
            return "success";
        }
        return "you have not access to update that user beacause you are not authenticated";

    }


}
