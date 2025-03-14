package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.request.UserRegisterRequest;

public interface UserService {
    void createUser(UserRegisterRequest payload);
}