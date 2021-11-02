package learn.events.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Event {
    private String id;
    private String title;
    private String Description;
    private Timestamp date;
    private int duration;
    private int capacity;
    private int eventLocationId;
    private String category;
    private int organizerId;
    private boolean status;

    public Event() {}

    public Event(String id, String title, String description, Timestamp date, int duration, int capacity, int eventLocationId, String category, int organizerId, boolean status) {
        this.id = id;
        this.title = title;
        Description = description;
        this.date = date;
        this.duration = duration;
        this.capacity = capacity;
        this.eventLocationId = eventLocationId;
        this.category = category;
        this.organizerId = organizerId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
        return duration == event.duration && capacity == event.capacity && eventLocationId == event.eventLocationId && organizerId == event.organizerId && status == event.status && Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(Description, event.Description) && Objects.equals(date, event.date) && Objects.equals(category, event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, Description, date, duration, capacity, eventLocationId, category, organizerId, status);
    }
}
