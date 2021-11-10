package learn.events.models;

public class EventUserBridge {
    private int eventId;
    private int userId;

    public EventUserBridge() {
    }

    public EventUserBridge(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
    }


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
