package learn.events.data;

import learn.events.models.UserEvent;

public interface UserEventRepository {
    boolean add(UserEvent userEvent);

    boolean deleteByKey(int userId, int eventId);
}
