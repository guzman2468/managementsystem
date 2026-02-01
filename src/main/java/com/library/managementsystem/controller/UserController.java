package com.library.managementsystem.controller;

import com.library.managementsystem.model.MessageResponse;
import com.library.managementsystem.model.user.*;
import com.library.managementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/createUser")
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        MessageResponse response = new MessageResponse();

        if (userService.checkDupeUserByEmail(request.email())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Email already designated to another user. Please try another email."));
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        if (request.role() != UserRole.LIBRARIAN && request.role() != UserRole.MEMBER) {
            response.setMessage("Invalid role detected.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        user.setRole(request.role());

        user.setPasswordHash(passwordEncoder.encode(request.password()));

        userService.createUser(user);

        response.setMessage("User added successfully.");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<MessageResponse> userLogin(@Valid @RequestBody UserLoginRequest request) {
        MessageResponse response = new MessageResponse();

        Optional<User> potentialUser = userService.findByEmail((request.email()));

        // check something was returned from DB query, if not provide purposely ambiguous error message
        if (potentialUser.isEmpty()) {
            response.setMessage("Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = potentialUser.get();

        boolean passwordMatch = passwordEncoder.matches(request.password(), user.getPasswordHash());

        if (!passwordMatch) {
            response.setMessage("Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.setMessage("Login successful, Welcome " + user.getName() + "!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    // moved to just confirmation opposed to display info
    @PostMapping("/getByNameAndEmail")
    public ResponseEntity<MessageResponse> getByNameAndEmail(@Valid @RequestBody NameEmailRequest request) {

        User matchedUser = userService.findByNameAndEmail(request.name(), request.email());

        if (matchedUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("User with that name and email not found"));
        }

        // to confirm successful response in the front end, going to have to use the 200 status code
        return ResponseEntity
                .ok(new MessageResponse("User found. Their details are not displayed for security reasons."));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<MessageResponse> deleteUser(@Valid @RequestBody NameEmailRequest request) {

        User matchedUser = userService.findByNameAndEmail(request.name(), request.email());

        if (matchedUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No user found with those details."));
        }

        userService.deleteUser(matchedUser);

        return ResponseEntity
                .ok(new MessageResponse("User successfully deleted"));
    }
}
