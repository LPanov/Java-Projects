package core;

import entities.user.User;

public class UserSessionManager implements SessionManager<User> {
    private User currentlyActive;

    public UserSessionManager() {
        this.currentlyActive = null;
    }

    @Override
    public User getActiveSession() {
        return currentlyActive;
    }

    @Override
    public void setActiveSession(User user) {
        this.currentlyActive = user;
    }

    @Override
    public boolean hasActiveSession() {
        return currentlyActive != null;
    }

    @Override
    public void terminateActiveSession() {
        currentlyActive = null;
    }
}
