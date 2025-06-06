package sk.tuke.fpa_tool_ws.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.request.UserRegisterRequest;
import sk.tuke.fpa_tool_ws.model.User;
import sk.tuke.fpa_tool_ws.service.impl.UserServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<Collection<User>> getUsers() {
        Collection<User> users =  userService.getAllUsers();
        return new ApiResponse<>(200, "Users retrieved successfully", users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ApiResponse<User> registerUser(@RequestBody UserRegisterRequest payload) {
        userService.createUser(payload);
        return new ApiResponse<>(200, "User registered successfully", null);
    }
}
