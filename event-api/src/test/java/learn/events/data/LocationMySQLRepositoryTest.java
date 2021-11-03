package learn.events.data;

import learn.events.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static learn.events.LocationTestHelper.makeValidLocation;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shouldFind3Locations() throws DataAccessException {
        var locations = repository.findAll();
        assertEquals(3, locations.size());
    }

    @Test
    void shouldFindId2() throws DataAccessException {
        Location expected = makeValidLocation(2);
        Location actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindId372() throws DataAccessException {
        assertNull(repository.findById(372));
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Location expected = makeValidLocation(4);
        Location in = makeValidLocation(0);
        Location actual = repository.add(in);
        assertEquals(expected, actual);

        actual = repository.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Location expected = makeValidLocation(2);
        expected.setTitle("New Title");
        expected.setCity("New City");

        assertTrue(repository.update(expected));

        Location actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        Location missing = makeValidLocation(202);
        assertFalse(repository.update(missing));
    }

    @Test
    void shouldDelete() throws DataAccessException {
        assertTrue(repository.deleteById(1));
        Location game = repository.findById(1);
        assertNull(game);
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        assertFalse(repository.deleteById(543));
    }
}
