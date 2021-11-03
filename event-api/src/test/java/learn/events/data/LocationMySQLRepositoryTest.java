package learn.events.data;

import learn.events.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static learn.events.LocationTestHelper.makeValidLocation;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationMySQLRepositoryTest {

    @Autowired
    LocationMySQLRepository repository;
    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFind3Locations() throws DataAccessException {
        var locations = repository.findAll();
        assertEquals(4, locations.size());
    }

    @Test
    void shouldFindId4() throws DataAccessException {
        Location expected = makeValidLocation(4);
        Location actual = repository.findById(4);
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
}
