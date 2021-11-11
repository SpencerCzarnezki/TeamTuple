package learn.events.data;


import learn.events.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserMySqlRepositoryTest {

    @Autowired
    UserRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup(){
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAllUsers3(){
        var users = repository.findAll();
        assertEquals(3,users.size());
    }

    @Test
    void shouldFindAllRoles2(){
        var roles = repository.findAllRoles();
        assertEquals(2,roles.size());
    }

    @Test
    void shouldFindByUsername(){
        var user = repository.findByUsername("Jdoe");
        assertEquals(user.getUserId(),1);
    }

    @Test
    void shouldFindById(){
        var user = repository.findByUserId(1);
        assertEquals(user.getUsername(),"Jdoe");
    }

    @Test
    void shouldAdd(){
        User user = new User();
        user.setUserId(4);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("Password1!");
        user.setDisabled(false);

        User added = new User();
        added.setFname("Tina");
        added.setLname("Smith");
        added.setUserName("TSmith");
        added.setEmail("Tsmith@email.com");
        added.setPassword("Password1!");
        added.setDisabled(false);

        User actual = repository.add(added);
        assertEquals(user,actual);

     }

    @Test
    void shouldUpdate(){

        User user = new User();
        user.setUserId(4);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("ghdgkjdkf");
        user.setDisabled(true);

        User add = repository.add(user);

        add.setUserName("Tsmith1");

        assertTrue(repository.update(add));

    }

    @Test
    void shouldNotUpdateMissing(){

        User user = new User();
        user.setUserId(55);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("ghdgkjdkf");
        user.setDisabled(true);
        assertFalse(repository.update(user));

    }








}
