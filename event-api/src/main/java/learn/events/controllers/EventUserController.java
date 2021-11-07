package learn.events.controllers;

import learn.events.domain.EventService;
import learn.events.domain.Result;
import learn.events.models.EventUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ConditionalOnWebApplication
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/event/user")
public class EventUserController {
    private final EventService eventService;

    public EventUserController(EventService eventService) {
        this.eventService = eventService;
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

}
