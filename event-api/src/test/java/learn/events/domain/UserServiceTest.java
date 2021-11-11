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
import static learn.events.TestHelper.*;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Test
    void shouldFindAll(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);
        User user1 = new User();
        user1.setUserId(2);
        user1.setFname("Dean");
        user1.setLname("Smith");
        user1.setUserName("DSmith");
        user1.setEmail("Dsmith@email.com");
        user1.setPassword("Password1!");
        user1.setDisabled(true);
        List<User> expected = List.of(user,user1);
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindValidId(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);
        when(repository.findByUserId(1)).thenReturn(user);

        User actual = service.findByUserId(1);

        assertEquals(user,actual);
    }

    @Test
    void shouldFindValidUsername(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);
        when(repository.findByUsername("TSmith"))
                .thenReturn(user);

        User actual = service.findByUsername("TSmith");
        assertEquals(user,actual);
    }

    @Test
    void shouldFindAllValidRoles(){
        String role1 = "USER";
        String role2 = "ADMIN";
        List<String> roles = List.of(role1,role2);
        when(repository.findAllRoles()).thenReturn(roles);
        List<String> actual = service.findAllRoles();
        assertEquals(roles,actual);
    }


    @Test
    void shouldRejectNullUser(){
        Result expected = makeResult("user cannot be null");
        Result actual = service.add(null);
        assertEquals(expected, actual);

    }

    @Test
    void shouldRejectNullUsername(){
        Result expected = makeResult("username is required");

        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName(null);
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);

        Result actual = service.add(user);
        assertEquals(expected, actual);

        user.setUserName("");
        assertEquals(expected, actual);
    }

    @Test
    void shouldRejectDuplicateUsername(){
        Result expected = makeResult("username is already in use");

        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("Jdoe");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);

        User user1 = new User();
        user1.setUserId(2);
        user1.setFname("Tina");
        user1.setLname("Smith");
        user1.setUserName("Jdoe");
        user1.setEmail("Tsmith@email.com");
        user1.setPassword("Password1!");
        user1.setDisabled(true);
        service.add(user);
        when(repository.findByUsername("Jdoe")).thenReturn(user);

        Result actual = service.add(user1);


        assertEquals(expected, actual);
    }


    @Test
    void shouldRejectShortPassword(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("Jdoe");
        user.setEmail("Tsmith@email.com");
        user.setDisabled(true);
        user.setPassword("");
        Result expected = makeResult("password must be at least 8 characters");

        Result actual = service.add(user);
        assertEquals(expected,actual);

        user.setPassword(null);
        Result actual1 = service.add(user);
        assertEquals(expected,actual1);

        user.setPassword("tr");
        Result actual2 = service.add(user);
        assertEquals(expected,actual2);
    }

    @Test
    void shouldRejectInvalidPassword(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("Jdoe");
        user.setEmail("Tsmith@email.com");
        user.setDisabled(true);
        user.setPassword("12345678a");
        Result expected = makeResult("password must contain a digit, a letter, and a non-digit/non-letter");

        Result actual = service.add(user);
        assertEquals(expected,actual);

        user.setPassword("1234567!");
        Result actual1 = service.add(user);
        assertEquals(expected,actual1);

        user.setPassword("!dgtgtgt");
        Result actual2 = service.add(user);
        assertEquals(expected,actual2);

    }


    @Test
    void shouldRejectSetUserId(){
        User user = new User();
        user.setUserId(1);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("Jdoe");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);
        user.setPassword("12345678a!");
        Result expected = makeResult("user id cannot be set before user creation");

        Result actual = service.add(user);
        assertEquals(actual,expected);
    }

    @Test
    void updateShouldRejectNotSetUserId() {
        User user = new User();
        user.setUserId(0);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("Jdoe");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(true);
        user.setPassword("12345678a!");
        service.add(user);
        user.setUserName("TSmith12");
        Result expected = makeResult("user id must be set for update");

        Result actual = service.update(user);
        assertEquals(expected,actual);
    }


}
