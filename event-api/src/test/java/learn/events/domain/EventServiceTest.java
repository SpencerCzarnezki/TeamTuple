package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.models.Event;
import learn.events.models.EventUser;
import learn.events.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static learn.events.TestHelper.makeResult;
import static org.junit.jupiter.api.Assertions.*;
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
        System.out.println(actual.getMessages());
        assertTrue(actual.isSuccess());

    }

    @Test
    void shouldNotAddPastDate(){
        Event event= makeEvent();
        event.setDate(Timestamp.valueOf(LocalDateTime.of(2017,12,12,12,00)));
        Result<Event> actual = service.add(event);
        Result expected = makeResult("Date must be in the future");
        assertFalse(actual.isSuccess());

        assertEquals(expected.getMessages(), actual.getMessages());
    }
    @Test
    void shouldNotAddNullEvent(){

        Result expected = makeResult("Event cannot be null");
        Result<Event> actual = service.add(null);
        assertFalse(actual.isSuccess());
        assertEquals(expected.getMessages(),actual.getMessages());
    }
    @Test
    void shouldNotAddNullTitle(){
        Event event = makeEvent();
        event.setTitle(null);
        Result expected = makeResult("Title is required");
        Result<Event> actual = service.add(event);
        assertFalse(actual.isSuccess());
        assertEquals(expected.getMessages(),actual.getMessages());
        event.setTitle("");
        Result<Event> actual2 = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());

    }
    @Test
    void shouldNotAddEmptyDateTime(){
        Event event = makeEvent();
        event.setDate(null);
        Result expected = makeResult("Date is required");
        Result<Event> actual = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());
    }
    @Test
    void shouldNotAddNullCategory(){
        Event event =makeEvent();
        event.setCategory(null);
        Result expected = makeResult("Category is required");
        Result<Event> actual = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());
        event.setCategory("   ");
        Result<Event> actual2 = service.add(event);
        assertEquals(expected.getMessages(),actual2.getMessages());
    }
    @Test
    void shouldNotAddNegativeCapacity(){
        Event event = makeEvent();
        event.setCapacity(0);
        Result expected = makeResult("Capacity must be greater than 0");
        Result<Event> actual = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());
    }
    @Test
    void shouldNotAddNegativeDuration(){
        Event event = makeEvent();
        event.setDuration(-1);
        Result expected = makeResult("Duration cannot be negative");
        Result<Event> actual = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());
    }
    @Test
    void shouldUpdate(){
        Event event = makeEvent();
        event.setId(1);
        when(repository.update(event)).thenReturn(true);
        Result<Event> actual = service.update(event);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidId(){
        Event event = makeEvent();
        event.setId(0);
        Result<Event> actual = service.update(event);
//        assertEquals(expected.getMessages(),actual.getMessages());
        assertFalse(actual.isSuccess());
    }
    @Test
    void shouldNotUpdateMissingEvent(){
        Event event = makeEvent();
        event.setId(2000);
        Result<Event> actual = service.update(event);
        assertFalse(actual.isSuccess());
    }
    @Test
    void shouldNotAddOrganizerAsAttendee(){
        Event event = makeEvent();
        event.setId(2);
        when(repository.findById(2)).thenReturn(event);
        EventUser eventUser = new EventUser();
        eventUser.setEventId(2);
        User user = makeUser();
        user.setUserId(2);


        service.add(event);


        eventUser.setUser(user);
        Result<Void> actual =service.addUser(eventUser);
        assertFalse(actual.isSuccess());
    }



    @Test
    void shouldNotAddDuplicate(){
        List<Event> events = List.of(makeEvent());
        Event event = makeEvent();
        when(repository.findAll()).thenReturn(events);

        Result expected = makeResult("Duplicate Event");
        Result<Event> actual = service.add(event);
        assertEquals(expected.getMessages(),actual.getMessages());
    }









    private Event makeEvent(){
        Event event = new Event();
        event.setTitle("New Event");
        event.setDescription("Super Cool Event");
        event.setDate(Timestamp.valueOf(LocalDateTime.of(2027,12,12,12,00)));
        event.setCategory("new Category");
        event.setOrganizerId(2);
        event.setDuration(120);
        event.setStatus(true);
        event.setEventLocationId(1);
        event.setCapacity(500);
        return event;
    }
    private User makeUser(){
        User user = new User();
        user.setUserId(4);
        user.setFname("Tina");
        user.setLname("Smith");
        user.setUserName("TSmith");
        user.setEmail("Tsmith@email.com");
        user.setPassword("ghdgkjdkf");
        user.setDisabled(true);
        return user;
    }

}