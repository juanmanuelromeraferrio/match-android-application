package com.match.client.entities.request;

/**
 * Created by Juan Manuel Romera on 2/6/2016.
 */
public class LoginRequest {

    private LoginUserContext user = null;

    public LoginRequest(String email, String password) {
        user = new LoginUserContext(email, password);
    }

    private final class LoginUserContext {
        String email;
        String password;

        public LoginUserContext(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
