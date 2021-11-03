package learn.events.data;

import learn.events.models.UserEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserEventJdbcRepository implements UserEventRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserEventJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(UserEvent userEvent){

        final String sql ="insert into app_user_event (app_user_id, app_event_id) values " +
                "(?, ?);";
        return jdbcTemplate.update(sql, userEvent.getUserId(), userEvent.getEventId()) > 0;
    }

    @Override
    public boolean deleteByKey(int userId, int eventId){
        final String sql ="delete from app_user_event " +
                "where app_user_id = ? and app_event_id = ?;";
        return jdbcTemplate.update(sql, userId, eventId) > 0;
    }
}
