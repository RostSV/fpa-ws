package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.dto.UserDto;
import sk.tuke.fpa_tool_ws.mapper.UserMapper;
import sk.tuke.fpa_tool_ws.model.User;
import sk.tuke.fpa_tool_ws.repository.UserRepository;
import sk.tuke.fpa_tool_ws.service.UserService;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserDto dto, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = userMapper.toEntity(dto);
        newUser.setPassword(hashedPassword);
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}