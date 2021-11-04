package learn.events.data;

import learn.events.models.Event;

import java.util.List;

public interface EventRepository {
    List<Event> findAll();

    Event findById(int eventId);

    List<Event> findByCategory(String category);

    List<Event> findByOrganizer(int organizerId);

    List<Event> findByKeyWord(String keyword);

    Event add(Event event);

    boolean update(Event event);

    boolean deleteById(int eventId);
}
