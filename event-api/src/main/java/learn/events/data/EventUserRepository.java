package learn.events.data;

import learn.events.models.EventUser;
import learn.events.models.UserEvent;

public interface EventUserRepository {
    boolean add(EventUser eventUser);

    boolean deleteByKey(int userId, int eventId);
}
