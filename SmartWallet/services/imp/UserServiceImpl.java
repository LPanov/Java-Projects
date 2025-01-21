package services.imp;

import core.UserSessionManager;
import entities.user.User;
import entities.user.UserImpl;
import repositories.UserRepository;
import services.UserService;

import java.util.List;

import static common.LogMessages.*;
import static common.SystemErrors.*;

// TODO:
// 1. Implement all methods
// 2. Make sure this service implementation has dependency a SessionManager
// so you can determine which is the currently logged in user.
public class UserServiceImpl implements UserService {

    private UserRepository users;
    private UserSessionManager sessionManager;

    public UserServiceImpl(UserSessionManager sessionManager) {
        this.users = new UserRepository();
        this.sessionManager = sessionManager;
    }

    @Override
    public String login(String username, String password) {
        if (sessionManager.hasActiveSession()) {
            String activeUser = sessionManager.getActiveSession().getUsername();
            throw new IllegalStateException(USER_ALREADY_LOGGED_IN.formatted(activeUser));
        }
        User user = users.getAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(INCORRECT_LOGIN_CREDENTIALS));

        sessionManager.setActiveSession(user);

        return SUCCESSFULLY_LOGGED_IN.formatted(username);
    }

    @Override
    public String register(String username, String password) {

        if (sessionManager.hasActiveSession()) {
            String activeUser = sessionManager.getActiveSession().getUsername();
            throw new IllegalStateException(USER_ALREADY_LOGGED_IN.formatted(activeUser));
        }

        boolean alreadyExist = users.getAll().stream().anyMatch(u -> u.getUsername().equals(username));
        if (alreadyExist) {
            throw new IllegalArgumentException(SUCH_USERNAME_ALREADY_EXIST.formatted(username));
        }

        User user = new UserImpl(username, password);
        users.save(user.getId(), user);
        login(username, password);

        return SUCCESSFULLY_REGISTERED.formatted(username);
    }

    @Override
    public String logout() {
        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        String activeUser = sessionManager.getActiveSession().getUsername();
        sessionManager.terminateActiveSession();
        return SUCCESSFULLY_LOGGED_OUT.formatted(activeUser);
    }

    @Override
    public List<User> getAllUsers() {
        return users.getAll();
    }
}
