package learn.events.data.mappers;

import learn.events.models.EventUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventUserMapper implements RowMapper<EventUser> {

    public EventUser mapRow(ResultSet resultSet, int i) throws SQLException {
        EventUser eventUser = new EventUser();
        eventUser.setEventId(resultSet.getInt("app_event_id"));

        UserMapper userMapper =new UserMapper();
        eventUser.setUser(userMapper.mapRow(resultSet, i));

        return eventUser;
    }

}
