package learn.events.models;

import java.util.Objects;

public class UserEvent {
    private int userId;
    private int eventId;

    public UserEvent() {
    }

    public UserEvent(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEvent userEvent = (UserEvent) o;
        return userId == userEvent.userId && eventId == userEvent.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
