package entities.user;

import java.util.UUID;

import static common.SystemErrors.INVALID_PASSWORD;
import static common.SystemErrors.INVALID_USERNAME;

public class UserImpl implements User{
    private UUID id;
    private String username;
    private String password;

    public UserImpl(String username, String password) {
        this.id = UUID.randomUUID();
        this.setUsername(username);
        this.setPassword(password);
    }

    private void setUsername(String username) {
        if (username.length() < 5 && !username.matches(".*\\d+.*")) {
            throw new IllegalArgumentException(INVALID_USERNAME);
        }
        this.username = username;
    }
    @Override
    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        if (!password.matches("\\d{6}")) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        this.password = password;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
