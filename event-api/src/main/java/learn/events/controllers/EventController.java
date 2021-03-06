package learn.events.controllers;

import learn.events.domain.EventService;
import learn.events.domain.Result;
import learn.events.models.Event;
import learn.events.models.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@ConditionalOnWebApplication
public class EventController {

    private final EventService eventService;


    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping
    public List<Event> findAll(){
        return eventService.findAll();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Event>> findByCategory(@PathVariable String category){
        List<Event> event = eventService.findByCategory(category);
        if (event == null || event.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(event);
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> findById(@PathVariable int eventId){
            Event event = eventService.findById(eventId);
            if (event == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        return ResponseEntity.ok(event);
    }
//    @GetMapping({"/"})
//    public ResponseEntity<List<Event>> findByOrganizer(@PathVariable int organizerId){
//        List<Event> event = eventService.findByOrganizer(organizerId);
//        if (event == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(event);
//    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<Event>> findByKeyWord(@PathVariable String keyword){
        List<Event> event = eventService.findByKeyWord(keyword);
        if (event == null || event.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Event event){
        Result<Event> result = eventService.add(event);
        if (result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{eventId}")
    public ResponseEntity<Object> update(@PathVariable int eventId, @RequestBody Event event){
        if (eventId != event.getId()){

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Event> result = eventService.update(event);
        if (result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteById(@PathVariable int eventId){
        if (eventService.deleteById(eventId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
