package sk.tuke.fpa_tool_ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sk.tuke.fpa_tool_ws.enums.Role;

import java.util.Set;

public class UserRegistrationRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Username to login", requiredMode = Schema.RequiredMode.REQUIRED, example = "superkevin")
    private String username;

    @Schema(description = "Email of the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    @NotBlank
    private String email;

    @Schema(description = "Password for the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "password123")
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(description = "Roles assigned to the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"ADMIN\"]")
    @NotNull
    private Set<Role> roles;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}