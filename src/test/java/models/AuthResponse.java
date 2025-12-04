package models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class AuthResponse {
    private String userId;
    private String username;
    private String password;
    private String token;
    private String expires;
    private String created_date;
    @JsonProperty("isActive")
    private boolean isActive;
}
