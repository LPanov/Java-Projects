package repositories;

import entities.user.User;

import java.util.*;

public class UserRepository implements Repository<User, UUID>{
    private Map<UUID, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(UUID uuid, User entity) {
        users.put(uuid, entity);
    }

    @Override
    public User getById(UUID uuid) {
        return users.get(uuid);
    }


    @Override
    public List<User> getAll() {
        return this.users.values().stream().toList();
    }
}
