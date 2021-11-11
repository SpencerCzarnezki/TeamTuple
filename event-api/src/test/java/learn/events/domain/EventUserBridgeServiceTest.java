package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.data.EventUserBridgeRepository;
import learn.events.data.KnownGoodStateNic;
import learn.events.data.UserRepository;
import learn.events.models.Event;
import learn.events.models.EventUserBridge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EventUserBridgeServiceTest {

    @Autowired
    EventUserBridgeService service;

    @MockBean
    EventUserBridgeRepository repository;

    @Autowired
    KnownGoodStateNic knownGoodState;

    @BeforeEach
    void setUp(){
        knownGoodState.set();
    }


    @Test
    void shouldAdd() {
        EventUserBridge bridge = new EventUserBridge(1,1);
        when(repository.add(bridge)).thenReturn(true);
        Result<EventUserBridge> actual = service.add(bridge);
        System.out.println(actual.getMessages());
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddBadEventId(){
        EventUserBridge bridge = new EventUserBridge(23,21);
        when(repository.add(bridge)).thenReturn(false);
        Result<EventUserBridge> actual = service.add(bridge);
        System.out.println(actual.getMessages());
        assertFalse(actual.isSuccess());
    }
}