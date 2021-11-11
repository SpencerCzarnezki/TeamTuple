package learn.events.domain;

import learn.events.data.EventRepository;
import learn.events.data.EventUserBridgeRepository;
import learn.events.data.UserRepository;
import learn.events.models.Event;
import learn.events.models.EventUserBridge;
import learn.events.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventUserBridgeService {
    private final EventUserBridgeRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventUserBridgeService(EventUserBridgeRepository repository, EventRepository eventRepository, UserRepository userRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    public List<EventUserBridge> findAll(){
        return repository.findAll();
    }
    public List<EventUserBridge> findByEventId(int eventId){
        return repository.findByEventId(eventId);
    }
    public List<EventUserBridge> findByUserId(int userId){
        return repository.findByUserId(userId);
    }
    public boolean deleteAllUsersInEventByEventId(int eventId){
        return repository.deleteAllEventAttendeesByEventId(eventId);
    }
    public boolean deleteUserFromAllEventsByUserId(int userId){
        return repository.deleteUserFromAllEventsById(userId);
    }
    //unused, deleteUserByKey in Event service does same function
    public boolean deleteOneUserFromOneEvent(int userId, int eventId){
        return repository.deleteOneUserFromOneEvent(userId,eventId);
    }
    public Result<EventUserBridge> add(EventUserBridge eventUserBridge){
        Result<EventUserBridge> result = validate(eventUserBridge);
        if (!result.isSuccess()){
            return result;
        }
        repository.add(eventUserBridge);
        result.setPayload(eventUserBridge);
        return result;

    }
    private Result<EventUserBridge> validate(EventUserBridge eventUserBridge){
        Result<EventUserBridge> result= new Result<>();
        if (eventUserBridge == null){
            result.addErrorMessage("Cannot add null event user bridge");
            return result;
        }
        if (eventUserBridge.getUserId() <= 0){
            result.addErrorMessage("Cannot add invalid userId");
            return result;
        }
        if (eventUserBridge.getEventId() <= 0){
            result.addErrorMessage("Cannot add invalid eventId");
            return result;
        }
        List<Event> allEvents = eventRepository.findAll();
        boolean check = false;
        for (Event e: allEvents){
            if (e.getId() == eventUserBridge.getEventId()){
                check= true;
            }
        }
        if (!check){
            result.addErrorMessage("EventId does not exist");
            return result;
        }
        List<User> allUser = userRepository.findAll();
        boolean userCheck = false;
        for (User u: allUser){
            if (u.getUserId() == eventUserBridge.getUserId()){
                userCheck = true;
            }
        }
        if (!userCheck){
            result.addErrorMessage("UserId does not exist");
            return result;
        }

        List<EventUserBridge> all = repository.findAll();
        for (EventUserBridge a:all){
            if (a.getEventId() == eventUserBridge.getEventId() && a.getUserId() == eventUserBridge.getUserId()){
                 result.addErrorMessage("Cannot Add User to the same event twice");
                 return result;
            }
        }
        return result;
    }
}
