package learn.events.data;


import learn.events.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventUserRepositoryTest {

    @Autowired
    EventUserRepository repository;

    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }
    @Test
    void shouldAdd(){
      EventUser eventUser = makeEvent();
      assertTrue(repository.add(eventUser));
    }
    @Test
    void shouldNotAdd(){
        EventUser userEvent = makeEvent();
        userEvent.setEventId(1);

        try{
            repository.add(userEvent);
        } catch (DuplicateKeyException ex){

        }
    }
    @Test
    void shouldDelete(){
        assertTrue(repository.deleteByKey(3,2));
        assertFalse(repository.deleteByKey(4,1));
    }

    private EventUser makeEvent(){

        EventUser eventUser = new EventUser();
        eventUser.setEventId(1);
        User user = new User();
        user.setUserId(2);
        user.setUserName("name");
        user.setEmail("email@email.com");
        user.setFname("Name");
        user.setLname("Last");
        eventUser.setUser(user);

        return eventUser;
    }





}