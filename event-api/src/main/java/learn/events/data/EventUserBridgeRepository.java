package learn.events.data;

import learn.events.models.EventUserBridge;

import java.util.List;

public interface EventUserBridgeRepository {
    List<EventUserBridge> findAll();

    List<EventUserBridge> findByEventId(int eventId);

    List<EventUserBridge> findByUserId(int userId);

    boolean add(EventUserBridge eventUserBridge);

    boolean deleteAllEventAttendeesByEventId(int id);

    boolean deleteUserFromAllEventsById(int id);

    boolean deleteOneUserFromOneEvent(int userId, int eventId);
}
