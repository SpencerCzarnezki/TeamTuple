package learn.events.models;

import java.util.Objects;

public class UserEvent {
    private int userId;


    private Event event;


    public UserEvent() {
    }

    public UserEvent(int userId) {
        this.userId = userId;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEvent userEvent = (UserEvent) o;
        return userId == userEvent.userId && Objects.equals(event, userEvent.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, event);
    }
}
