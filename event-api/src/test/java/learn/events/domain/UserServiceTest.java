package learn.events.domain;


import learn.events.data.DataAccessException;
import learn.events.data.UserRepository;
import learn.events.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Test
    void shouldFindAll() throws DataAccessException{
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPasswordHash("ghdgkjdkf");
        user.setDisabled(true);
        User user1 = new User();
        user1.setUserId(2);
        user1.setFname("Dean");
        user1.setLname("Smith");
        user1.setUserName("DSmith");
        user1.setEmail("Dsmith@email.com");
        user1.setPasswordHash("ghdgkjdkf");
        user1.setDisabled(true);
        List<User> expected = List.of(user,user1);
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected,actual);
    }

}
