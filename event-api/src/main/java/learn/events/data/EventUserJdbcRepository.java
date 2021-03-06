package learn.events.data;

import learn.events.data.mappers.EventMapper;
import learn.events.data.mappers.EventUserMapper;
import learn.events.models.EventUser;
import learn.events.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventUserJdbcRepository implements EventUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventUserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    

    @Override
    public boolean add(EventUser eventUser){

        final String sql ="insert into app_user_event (app_user_id, app_event_id) values " +
                "(?, ?);";
        return jdbcTemplate.update(sql, eventUser.getUser().getUserId(), eventUser.getEventId()) > 0;
    }

    @Override
    public boolean deleteByKey(int userId, int eventId){
        final String sql ="delete from app_user_event " +
                "where app_user_id = ? and app_event_id = ?;";
        return jdbcTemplate.update(sql, userId, eventId) > 0;
    }
}
