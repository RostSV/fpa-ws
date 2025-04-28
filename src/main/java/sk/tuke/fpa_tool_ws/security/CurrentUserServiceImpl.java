package sk.tuke.fpa_tool_ws.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.tuke.fpa_tool_ws.security.detail.CustomUserDetails;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUserId();
            } else {
                throw new SecurityException("Authentication principal is not of type CustomUserDetails. Try check authentication token.");
            }
        }
        return null;
    }
}