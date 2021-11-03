package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.models.Event;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class EventService {

    private final EventRepository repository;


    public EventService(EventRepository repository) {
        this.repository = repository;
    }
    public List<Event> findAll(){
        return repository.findAll();
    }
    public List<Event> findByCategory(String category){
        return repository.findByCategory(category);
    }
    public List<Event> findByKeyWord(String keyword){
        return repository.findByKeyWord(keyword);
    }
    public List<Event> findByOrganizer(int organizerId){
        return  repository.findByOrganizer(organizerId);
    }
    public boolean deleteById(int eventId){
        return repository.deleteById(eventId);
    }

    public Result<Event> add(Event event){
        Result<Event>result = validate(event);
        if (!result.isSuccess()){
            return result;
        }
        if (event.getId() !=0){
            result.addErrorMessage("eventId must not be set to add an event");
        }
        event = repository.add(event);
        result.setPayload(event);
        return result;
    }
    public Result<Event> update(Event event){
        Result<Event>result = validate(event);
        if (!result.isSuccess()){
            return result;
        }
        if (event.getId() < 0 ){
            result.addErrorMessage("EventId is required to update event");
        }
        boolean success = repository.update(event);
        if (!success){
            result.addErrorMessage("Could not update event. Event ID: " + event.getId());
        }
        return result;
    }

    private Result<Event> validate(Event event) {
        Result<Event> result= new Result<>();
        if (event == null){
            result.addErrorMessage("Event cannot be null");
            return result;
        }
        if (event.getTitle() == null || event.getTitle().isBlank()){
            result.addErrorMessage("Title is required");
            return result;
        }
        if (event.getDate() == null){
            result.addErrorMessage("Date is required");
            return result;
        }
        if (event.getDate().before(Timestamp.valueOf(LocalDateTime.now()))){
            result.addErrorMessage("Date must be in the future");
            return result;
        }
        if (event.getCategory() == null || event.getCategory().isBlank()){
            result.addErrorMessage("Category is required");
            return result;
        }
        if (event.getCategory() == null || event.getCategory().isBlank()){
            result.addErrorMessage("Category is required");
            return result;
        }
        if (event.getCapacity() <= 0){
            result.addErrorMessage("Capacity must be greater than 0");
            return result;
        }
        if (event.getOrganizerId() < 0){
            result.addErrorMessage("Capacity must be greater than 0");
            return result;
        }



        return result;
    }

}
