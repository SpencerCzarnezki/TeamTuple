//package learn.events.data;
//
//
//import learn.events.models.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//public class UserMySqlRepositoryTest {
//
//    @Autowired
//    UserRepository repository;
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//
//    @BeforeEach
//    void setup(){
//        jdbcTemplate.update("call set_known_good_state();");
//    }
//
//    @Test
//    void shouldFindAllUsers3() throws DataAccessException{
//        var users = repository.findAll();
//        assertEquals(3,users.size());
//    }
//
//    @Test
//    void shouldFindAllRoles2() throws DataAccessException{
//        var roles = repository.findAllRoles();
//        assertEquals(2,roles.size());
//    }
//
//    @Test
//    void shouldFindByUsername() throws DataAccessException{
//        var user = repository.findByUsername("Jdoe");
//        assertEquals(user.getUserId(),1);
//    }
//
//    @Test
//    void shouldFindById() throws DataAccessException{
//        var user = repository.findByUserId(1);
//        assertEquals(user.getUsername(),"Jdoe");
//    }
//
//    @Test
//    void shouldAdd() throws DataAccessException{
//        User user = new User();
//        user.setUserId(4);
//        user.setFname("Tina");
//        user.setLname("Smith");
//        user.setUserName("TSmith");
//        user.setEmail("Tsmith@email.com");
//        user.setPasswordHash("ghdgkjdkf");
//        user.setDisabled(true);
//
//        User added = new User();
//        added.setUserId(0);
//        added.setFname("Tina");
//        added.setLname("Smith");
//        added.setUserName("TSmith");
//        added.setEmail("Tsmith@email.com");
//        added.setPasswordHash("ghdgkjdkf");
//        added.setDisabled(true);
//
//        User actual = repository.add(added);
//        assertEquals(user,actual);
//
//        actual= repository.findByUserId(4);
//        assertEquals(user,actual);
//    }
//
//    @Test
//    void shouldUpdate() throws DataAccessException{
//
//        User user = new User();
//        user.setUserId(4);
//        user.setFname("Tina");
//        user.setLname("Smith");
//        user.setUserName("TSmith");
//        user.setEmail("Tsmith@email.com");
//        user.setPasswordHash("ghdgkjdkf");
//        user.setDisabled(true);
//
//        User add = repository.add(user);
//
//        add.setUserName("Tsmith1");
//
//        assertTrue(repository.update(add));
//        User actual = repository.findByUserId(4);
//        assertEquals(actual,add);
//    }
//
//    @Test
//    void shouldNotUpdateMissing() throws DataAccessException {
//
//        User user = new User();
//        user.setUserId(55);
//        user.setFname("Tina");
//        user.setLname("Smith");
//        user.setUserName("TSmith");
//        user.setEmail("Tsmith@email.com");
//        user.setPasswordHash("ghdgkjdkf");
//        user.setDisabled(true);
//        assertFalse(repository.update(user));
//
//    }
//
//
//
//
//
//
//
//
//}
