package denis_sofia.mavenproject1.service;

import org.springframework.stereotype.Service;
import denis_sofia.mavenproject1.domain.User;
import denis_sofia.mavenproject1.exception.InvalidPasswordException;
import denis_sofia.mavenproject1.exception.UserAlreadyExistsException;
import denis_sofia.mavenproject1.exception.UserNotFoundException;
import denis_sofia.mavenproject1.repository.UserBase;

@Service
public class UserService {
    private final UserBase userBase;

    public UserService(UserBase userBase) {
        this.userBase = userBase;
    }

    public void register(String login, String password) throws UserAlreadyExistsException {
        if (userBase.exists(login)) {
            throw new UserAlreadyExistsException("User already exists");
        }
        userBase.save(new User(login, password));
    }

    public User login(String login, String password) throws UserNotFoundException, InvalidPasswordException {
        User user = userBase.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }
}