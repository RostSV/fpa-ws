package sk.tuke.fpa_tool_ws.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRoles()
        );
    }


}
