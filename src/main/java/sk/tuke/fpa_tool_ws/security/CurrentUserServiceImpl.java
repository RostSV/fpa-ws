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
        if(authentication != null){
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            return principal.getUserId();
        }
        return null;
    }
}
