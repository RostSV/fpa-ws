package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.UserDto;

public interface UserService {
    void createUser(UserDto dto, String password);
}
