package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventServiceTest {

    @Autowired
    EventService service;

    @MockBean
    EventRepository repository;

    @Test
    void shouldAdd(){
        Event event = makeEvent();
        Event mock =makeEvent();
        mock.setId(1);
        when(repository.add(event)).thenReturn(mock);
        Result<Event> actual = service.add(event);
        assertTrue(actual.isSuccess());

    }

    @Test
    void shouldNotAddPastDate(){

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