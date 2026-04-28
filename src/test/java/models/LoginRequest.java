package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public static class Builder {
        private String email;
        private String password;

        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public LoginRequest build() { return new LoginRequest(email, password); }
    }
}
