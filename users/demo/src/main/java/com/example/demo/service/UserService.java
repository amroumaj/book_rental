package com.example.demo.service;


import com.example.demo.config.PasswordUtil;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegistrationDTO;
import com.example.demo.dto.UpdateDTO;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public void addUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        PasswordUtil passwordUtil = new PasswordUtil();
        String hashedPASword = passwordUtil.hashPassword(registrationDTO.getPassword());
        user.setPassword(hashedPASword);
        user.setStatus(Status.LOGGED_OUT);
        userRepository.save(user);
    }

    public boolean Login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        PasswordUtil passwordUtil = new PasswordUtil();
        Boolean isPasswordMatch = passwordUtil.verifyPassword(loginDTO.getPassword(), user.getPassword());
        if (isPasswordMatch) {
            user.setStatus(Status.LOGGED_IN);
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    public User getUserDetails(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean UpdateUser(UpdateDTO updateDTO) {
        User user = userRepository.findByUsername(updateDTO.getUsername());
        PasswordUtil passwordUtil = new PasswordUtil();
        Boolean isPasswordMatch = passwordUtil.verifyPassword(updateDTO.getOldPassword(), user.getPassword());
        if (isPasswordMatch) {
            String hashedPASword = passwordUtil.hashPassword(updateDTO.getNewPassword());
            user.setPassword(hashedPASword);
            user.setStatus(updateDTO.getStatus());
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }
}
