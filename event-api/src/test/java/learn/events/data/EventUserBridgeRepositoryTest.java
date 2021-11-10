package learn.events.data;

import learn.events.models.EventUserBridge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventUserBridgeRepositoryTest {

    @Autowired
    EventUserBridgeRepository repository;

    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<EventUserBridge> eventUserBridges = repository.findAll();
        assertNotNull(eventUserBridges);
    }
    @Test
    void shouldFindByEventId(){
        List<EventUserBridge> eventUserBridges = repository.findByEventId(1);
        assertNotNull(eventUserBridges);
    }
    @Test
    void shouldFindByUserId(){
        List<EventUserBridge> eventUserBridges = repository.findByUserId(1);
        assertNotNull(eventUserBridges);
    }


}