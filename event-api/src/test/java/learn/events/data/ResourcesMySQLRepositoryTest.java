package learn.events.data;

import learn.events.models.Event;
import learn.events.models.Location;
import learn.events.models.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static learn.events.TestHelper.makeValidLocation;
import static learn.events.TestHelper.makeValidResource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ResourcesMySQLRepositoryTest {

    @Autowired
    ResourcesMySQLRepository repository;
    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFind4Resources() throws DataAccessException {
        Resources expected = makeValidResource(5);
        var resources = repository.findAll();
        assertEquals(4, resources.size());
    }

    @Test
    void shouldFindByDescription(){
        List<Resources> resources = repository.findByDescription("Description");
        assertEquals(1, resources.size());
    }

    @Test
    void shouldFindId5() throws DataAccessException {
        Resources expected = makeValidResource(5);
        Resources actual = repository.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindId188() throws DataAccessException {
        assertNull(repository.findById(188));
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Resources expected = makeValidResource(5);
        Resources in = makeValidResource(0);
        Resources actual = repository.add(in);
        assertEquals(expected, actual);

        actual = repository.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDelete() throws DataAccessException {
        assertTrue(repository.deleteById(1));
        Resources resource = repository.findById(1);
        assertNull(resource);
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        assertFalse(repository.deleteById(25));
    }

}
