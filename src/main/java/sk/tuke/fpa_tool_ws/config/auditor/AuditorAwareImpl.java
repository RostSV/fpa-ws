package sk.tuke.fpa_tool_ws.config.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import sk.tuke.fpa_tool_ws.security.CurrentUserService;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    private final CurrentUserService currentUserService;

    public AuditorAwareImpl(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return this.currentUserService.getUserId().describeConstable();
    }
}
