package sk.tuke.fpa_tool_ws.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.UserRegistrationRequest;
import sk.tuke.fpa_tool_ws.model.User;
import sk.tuke.fpa_tool_ws.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<Collection<User>> getUsers() {
        Collection<User> users =  userService.getAllUsers();
        return new ApiResponse<>(200, "Users retrieved successfully", users);
    }

    @PostMapping("/register")
    public ApiResponse<User> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        User user =  userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRoles()
        );

        return new ApiResponse<>(200, "User registered successfully", user);
    }

}
