package denis_sofia.mavenproject1.repository;

import denis_sofia.mavenproject1.domain.User;
import java.util.HashMap;

public class UserBase {
    private HashMap<String, User> storage;

    public UserBase() {
        storage = new HashMap<>();
    }

    public User findByLogin(String login) {
        return storage.get(login);
    }

    public void save(User user) {
        storage.put(user.getLogin(), user);
    }

    public boolean exists(String login) {
        return storage.containsKey(login);
    }
}