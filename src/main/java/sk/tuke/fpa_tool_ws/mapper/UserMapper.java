package sk.tuke.fpa_tool_ws.mapper;

import org.springframework.stereotype.Component;
import sk.tuke.fpa_tool_ws.dto.UserDto;
import sk.tuke.fpa_tool_ws.dto.request.UserRegisterRequest;
import sk.tuke.fpa_tool_ws.model.User;

@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setEnabled(user.isEnabled());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        user.setEnabled(userDto.isEnabled());
        return user;
    }

    public static User toEntity(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        user.setRoles(userRegisterRequest.getRoles());
        return user;
    }
}
