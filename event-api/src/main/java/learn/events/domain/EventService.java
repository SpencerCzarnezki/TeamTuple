package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.data.EventUserRepository;
import learn.events.data.UserRepository;
import learn.events.models.Event;
import learn.events.models.EventUser;
import learn.events.models.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class EventService {

    private final EventRepository repository;
    private final EventUserRepository userEventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository repository, EventUserRepository userEventRepository, UserRepository userRepository) {
        this.repository = repository;
        this.userEventRepository = userEventRepository;
        this.userRepository = userRepository;
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
        if (event.getId() <= 0 ){
            result.addErrorMessage("EventId is required to update event");
            return result;
        }
        boolean success = repository.update(event);
        if (!success){
            result.addErrorMessage("Could not update event. Event ID: " + event.getId());
        }
        return result;
    }
    public Result<Void> addUser(EventUser eventUser) {
        Result<Void>result = validate(eventUser);
        if (!result.isSuccess()){
            return result;
        }
        if (!userEventRepository.add(eventUser)){
            result.addErrorMessage("User not added to event");
        }
        return result;
    }
    public boolean deleteUserByKey(int userId, int eventId){
        return userEventRepository.deleteByKey(userId, eventId);
    }

    private  Result<Void> validate(EventUser eventUser){
        Result<Void> result =new Result<>();
        if (eventUser == null){
            result.addErrorMessage("EventUser cannot be null");
            return result;
        }
        if (eventUser.getUser() == null ){
            result.addErrorMessage("User cannot be null");
            return result;
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
        if (event.getCapacity() <= 0){
            result.addErrorMessage("Capacity must be greater than 0");
            return result;
        }
        if (event.getOrganizerId() < 0){
            result.addErrorMessage("Capacity must be greater than 0");
            return result;
        }
        if (event.getDuration() < 0){
            result.addErrorMessage("Duration cannot be negative");
            return result;
        }
        List<Event> all = findAll();
        for (Event e: all){
            System.out.println(e.getTitle());
            if (e.getOrganizerId() == event.getOrganizerId() && e.getTitle() == event.getTitle()
            && e.getEventLocationId() == event.getEventLocationId() && e.getDate() == event.getDate()){
                result.addErrorMessage("Duplicate Event");
                return result;
            }
        }

        //need to test
        boolean check = false;
        List<User> users = userRepository.findAll();
        for (User u: users){
            if (u.getUserId() == event.getOrganizerId()){
                check = true;
            }
        }
        if (check){
            result.addErrorMessage("Organizer Cannot join own event");
            return result;
        }




        return result;
    }

}
