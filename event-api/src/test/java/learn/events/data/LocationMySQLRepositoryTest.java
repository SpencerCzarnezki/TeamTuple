package learn.events.data;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationMySQLRepositoryTest {

    @Autowired
    LocationMySQLRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }


}
