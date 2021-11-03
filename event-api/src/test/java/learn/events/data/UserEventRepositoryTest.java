package learn.events.data;


import learn.events.domain.Result;
import learn.events.models.User;
import learn.events.models.UserEvent;
import learn.events.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserEventRepositoryTest {

    @Autowired
    UserEventRepository repository;

    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }
    @Test
    void shouldAdd(){
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(2);
        userEvent.setEventId(1);
        assertTrue(repository.add(userEvent));
    }
    @Test
    void shouldNotAdd(){
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(3);
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




}