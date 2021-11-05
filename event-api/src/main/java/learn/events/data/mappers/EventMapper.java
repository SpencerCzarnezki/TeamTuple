package learn.events.data.mappers;

import learn.events.models.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EventMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("eventId"));
        event.setTitle(resultSet.getString("title"));
        event.setDescription(resultSet.getString("description"));
        event.setDate(resultSet.getTimestamp("event_date"));
        event.setDuration(resultSet.getInt("duration"));
        event.setCapacity(resultSet.getInt("capacity"));
        event.setEventLocationId(resultSet.getInt("eventLocationId"));
        event.setCategory(resultSet.getString("category"));
        event.setOrganizerId(resultSet.getInt("organizerId"));
        event.setStatus(resultSet.getBoolean("status"));

        return event;
    }

}
