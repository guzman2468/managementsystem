package com.library.managementsystem.controller;

import com.library.managementsystem.model.user.User;
import com.library.managementsystem.model.MessageResponse;
import com.library.managementsystem.model.user.UserResponse;
import com.library.managementsystem.model.user.UserRole;
import com.library.managementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody User user) {

        MessageResponse response = new MessageResponse();
        String name = user.getName();
        String email = user.getEmail();
        UserRole role = user.getRole();

        boolean isPresentByEmail = userService.checkDupeUserByEmail(email);

        if (isPresentByEmail) {
            response.setMessage("Email already desginated to another user. Please try another email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            userService.createUser(new User(name, email, role));
            response.setMessage("User added successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @GetMapping("/getByNameAndEmail")
    public UserResponse getByNameAndEmail(@Valid @RequestBody User user) {

        UserResponse response = new UserResponse();
        String name = user.getName();
        String email = user.getEmail();

        User matchedUser = userService.findByNameAndEmail(name, email);

        if (matchedUser != null) {
//            response.setId(matchedUser.getId());
            response.setName(matchedUser.getName());
            response.setEmail(matchedUser.getEmail());
            response.setRole(String.valueOf(matchedUser.getRole()));
        } else {
            response.setMessage("User with that name and email not found");
        }
        return response;
    }


    @DeleteMapping
    public UserResponse deleteUser(@Valid @RequestBody User user) {

        UserResponse response = new UserResponse();
        String name = user.getName();
        String email =  user.getEmail();

        User matchedUser = userService.findByNameAndEmail(name, email);

        if (matchedUser != null) {
            userService.deleteUser(matchedUser);
            response.setMessage("User successfully deleted");
        } else {
            response.setMessage("No user found with those details.");
        }

        return response;
    }
    //updateEmail endpoint scrapped for NOW, as locating user by email requires JWT auth
}


