package learn.events.data.mappers;

import learn.events.models.Event;
import learn.events.models.UserEvent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEventMapper implements RowMapper<UserEvent> {

    public UserEvent mapRow(ResultSet resultSet, int i) throws SQLException {
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(resultSet.getInt("app_user_id"));

        EventMapper eventMapper = new EventMapper();
        userEvent.setEvent(eventMapper.mapRow(resultSet, i));

        return userEvent;
    }

}
