package learn.events.data;

import learn.events.models.Event;
import learn.events.models.EventUserBridge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EventUserBridgeJdbcRepository implements EventUserBridgeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<EventUserBridge> mapper = (resultSet, rowNum) -> {
            EventUserBridge eventUserBridge = new EventUserBridge();
            eventUserBridge.setUserId(resultSet.getInt("app_user_id"));
            eventUserBridge.setEventId(resultSet.getInt("app_event_id"));
            return eventUserBridge;
    };

    public EventUserBridgeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<EventUserBridge> findAll(){
        final String sql = "select * from app_user_event;";
        return jdbcTemplate.query(sql, mapper);
    }
    @Override
    public List<EventUserBridge> findByEventId(int eventId){
        final String sql = "select * from app_user_event where app_event_id=?;";
        return jdbcTemplate.query(sql, mapper, eventId);
    }
    @Override
    public List<EventUserBridge> findByUserId(int userId){
        final String sql = "select * from app_user_event where app_user_id=?;";
        return jdbcTemplate.query(sql, mapper, userId);
    }


    @Override
    public boolean add(EventUserBridge eventUserBridge) {
        final String sql = "insert into app_user_event " +
                "(app_user_id, app_event_id) " +
                "values (?,?);";
        int rowsAffected = jdbcTemplate.update(sql,
                eventUserBridge.getUserId(),
                eventUserBridge.getEventId());

        return rowsAffected > 0;
    }
    @Override
    public boolean deleteAllEventAttendeesByEventId(int id){
        int rowsAffected = jdbcTemplate.update("delete from app_user_event where app_event_id = ?;", id);
        return rowsAffected > 0;
    }
    @Override
    public boolean deleteUserFromAllEventsById(int id){
        int rowsAffected = jdbcTemplate.update("delete from app_user_event where app_user_id = ?;", id);
        return rowsAffected > 0;
    }
    @Override
    public boolean deleteOneUserFromOneEvent(int userId, int eventId){
        int rowsAffected = jdbcTemplate.update("delete from app_user_event where app_user_id = ? and app_event_id = ?;",userId, eventId);
        return rowsAffected > 0;
    }



}
