package learn.events.data;

import learn.events.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventRepositoryTest {

    @Autowired
    EventJdbcTemplateRepository repository;

    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setUp(){
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Event> events =repository.findAll();
        assertNotNull(events);
        assertEquals(3,events.size());
    }
    @Test
    void shouldFindByCategory(){
        List<Event> events=repository.findByCategory("Games");
        assertEquals(1, events.size());
    }
    @Test
    void shouldNotFindByCategory(){
        List<Event> events=repository.findByCategory("Game");
        assertNull(events);
    }
    @Test
    void shouldFindByKeyWord(){
        List<Event> events=repository.findByKeyWord("learn");
        assertEquals(1,events.size());
        events = repository.findByKeyWord("meet");
        assertEquals(1,events.size());

    }
    @Test
    void shouldNotFindByKeyWord(){
        List<Event> events=repository.findByKeyWord("learns");
        assertNull(events);
        events=repository.findByKeyWord("");
        assertNull(events);
    }
    @Test
    void shouldFindByOrganizer(){
        List<Event> events = repository.findByOrganizer(1);
        assertEquals(1, events.size());
        Event event = events.get(0);
        assertEquals("Meet Up", event.getTitle());
    }
    @Test
    void shouldNotFindByOrganizer(){
        List<Event> events = repository.findByOrganizer(4);
        assertNull(events);
    }
    @Test
    void shouldAdd(){
        Event event = makeEvent();
        Event actual = repository.add(event);
        assertNotNull(actual);
        assertEquals(4, actual.getId());
    }
    @Test
    void shouldNotAdd(){
        Event event = makeEvent();
        event.setTitle("");
        Event actual = repository.add(event);
        assertNull(actual);
    }
    @Test
    void shouldUpdate(){
        Event event = makeEvent();
        event.setId(3);
        assertTrue(repository.update(event));
    }
    @Test
    void shouldNotUpdate(){
        Event event = makeEvent();
        event.setId(20);
        assertFalse(repository.update(event));
    }
    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }



    private Event makeEvent(){
        Event event = new Event();
        event.setTitle("New Event");
        event.setDescription("Super Cool Event");
        event.setDate(Timestamp.valueOf(LocalDateTime.now()));
        event.setCategory("new Category");
        event.setOrganizerId(2);
        event.setDuration(120);
        event.setStatus(true);
        event.setEventLocationId(1);
        event.setCapacity(500);
        return event;
    }


}