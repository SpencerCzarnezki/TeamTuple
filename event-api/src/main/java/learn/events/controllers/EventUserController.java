package learn.events.controllers;

import learn.events.domain.EventService;
import learn.events.domain.EventUserBridgeService;
import learn.events.domain.Result;
import learn.events.models.Event;
import learn.events.models.EventUser;
import learn.events.models.EventUserBridge;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ConditionalOnWebApplication
@RequestMapping("/api/event/user")
public class EventUserController {
    private final EventService eventService;
    private final EventUserBridgeService eventUserBridgeService;

    public EventUserController(EventService eventService, EventUserBridgeService eventUserBridgeService) {
        this.eventService = eventService;
        this.eventUserBridgeService = eventUserBridgeService;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody EventUser eventUser){
        Result<Void> result = eventService.addUser(eventUser);
        if (result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{eventId}/{userId}")
    public ResponseEntity<Void> deleteByKey(@PathVariable int userId, @PathVariable int eventId){
        if (eventService.deleteUserByKey(userId, eventId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addEventUserBridge(@RequestBody EventUserBridge eventUserBridge){
        Result<EventUserBridge> result = eventUserBridgeService.add(eventUserBridge);
        if (result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete/event/{eventId}")
    public ResponseEntity<Void> deleteAllAttendeesByEventId(@PathVariable int eventId){
        if (eventUserBridgeService.deleteAllUsersInEventByEventId(eventId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<Void> deleteUserFromAllEventsById(@PathVariable int userId){
        if (eventUserBridgeService.deleteUserFromAllEventsByUserId(userId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public List<EventUserBridge> findAll(){
        return eventUserBridgeService.findAll();
    }

    @GetMapping("/find/event/{eventId}")
    public ResponseEntity<List<EventUserBridge>> findUsersByEventId(@PathVariable int eventId) {
        List<EventUserBridge> event = eventUserBridgeService.findByEventId(eventId);
        if (event == null || event.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(event);
    }
    @GetMapping("/find/user/{userId}")
    public ResponseEntity<List<EventUserBridge>> findUsersByUserId(@PathVariable int userId) {
        List<EventUserBridge> event = eventUserBridgeService.findByUserId(userId);
        if (event == null || event.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(event);
    }

}
