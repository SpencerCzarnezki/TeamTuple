package learn.events.domain;

import learn.events.data.UserRepository;
import learn.events.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }
    public List<User> findAll() {
        return repository.findAll();
    }

    public List<String> findAllRoles() {
        return repository.findAllRoles();
    }

    public User findByAppUserId(int id) {
        return repository.findByUserId(id);
    }

    public Result<User> add(User user) {

        Result<User> result = validateWithoutPassword(user);
        if (!result.isSuccess()) {
            return result;
        }

        result = validatePassword(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addErrorMessage("user id cannot be set before user creation");
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user = repository.add(user);
        if (user == null) {
            result.addErrorMessage("user not created.");
        }
        result.setPayload(user);

        return result;
    }


    public Result<User> update(User user) {
        Result<User> result = validateWithoutPassword(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addErrorMessage("user id must be set for update");
            return result;
        }

        boolean success = repository.update(user);
        if (!success) {
            result.addErrorMessage("user not updated");
        }

        return result;
    }

    public Result<User> changePassword(User user) {
        Result<User> result = validatePassword(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addErrorMessage("user id must be set to change password");
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));
        boolean success = repository.changePassword(user);
        if (!success) {
            result.addErrorMessage("password not updated");
        }

        return result;
    }

    private Result<User> validateWithoutPassword(User user) {
        var result = new Result<User>();

        if (user == null) {
            result.addErrorMessage("user cannot be null");
            return result;
        }

        if (user.getUserName() == null || user.getUserName().isBlank()) {
            result.addErrorMessage("username is required");
            return result;
        }

        var existing = repository.findByUsername(user.getUserName());
        if (existing != null && existing.getUserId() != user.getUserId()) {
            result.addErrorMessage("username is already in use");
            return result;
        }

        return result;
    }

    private Result<User> validatePassword(User user) {

        var result = new Result<User>();

        if (user == null) {
            result.addErrorMessage("user cannot be null");
            return result;
        }

        if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < 8) {
            result.addErrorMessage("password must be at least 8 characters");
            return result;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : user.getPassword().toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            result.addErrorMessage("password must contain a digit, a letter, and a non-digit/non-letter");
        }

        return result;
    }
}
