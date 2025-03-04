package sk.tuke.fpa_tool_ws.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.enums.Role;
import sk.tuke.fpa_tool_ws.model.User;
import sk.tuke.fpa_tool_ws.repository.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email, String password, Set<Role> roles) {
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}