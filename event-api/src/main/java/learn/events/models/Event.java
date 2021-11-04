package learn.events.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event {
    private int id;
    private String title;
    private String description;
    private Timestamp date;
    private int duration;
    private int capacity;
    private int eventLocationId;
    private String category;
    private int organizerId;
    private boolean status;


    private List<EventUser> attendees =new ArrayList<>();


    public List<EventUser> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<EventUser> attendees) {
        this.attendees = attendees;
    }



    public Event() {}

    public Event(int id, String title, String description, Timestamp date, int duration, int capacity, int eventLocationId, String category, int organizerId, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.capacity = capacity;
        this.eventLocationId = eventLocationId;
        this.category = category;
        this.organizerId = organizerId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEventLocationId() {
        return eventLocationId;
    }

    public void setEventLocationId(int eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return duration == event.duration && capacity == event.capacity && eventLocationId == event.eventLocationId && organizerId == event.organizerId && status == event.status && Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(description, event.description) && Objects.equals(date, event.date) && Objects.equals(category, event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, date, duration, capacity, eventLocationId, category, organizerId, status);
    }
}
