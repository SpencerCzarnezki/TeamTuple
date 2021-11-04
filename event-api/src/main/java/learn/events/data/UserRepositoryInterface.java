package learn.events.data;

import learn.events.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepositoryInterface {
    List<User> findAll();

    List<String> findAllRoles();

    @Transactional
    User findByUsername(String username);

    User findByUserId(int id);

    User add(User user);

    boolean update(User user);

    boolean changePassword(User user);
}
