package learn.events.data;

import learn.events.data.mappers.EventMapper;
import learn.events.data.mappers.UserEventMapper;
import learn.events.models.Event;
import learn.events.models.UserEvent;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository

public class EventJdbcTemplateRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Event> findAll() {
        final String sql ="select eventId, title, `description`, event_date, duration, capacity, eventLocationId, " +
                "category, organizerId, `status` from `event`";
        return jdbcTemplate.query(sql, new EventMapper());
    }
    @Override
    public List<Event> findByCategory(String category){
        final String sql = "select eventId, title, `description`, event_date, duration, capacity, eventLocationId, " +
                "category, organizerId, `status` from `event` where category=?";
        try {
            return jdbcTemplate.query(sql, new EventMapper(), category);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    @Override
    public List<Event> findByOrganizer(int organizerId){
        final String sql = "select eventId, title, `description`, event_date, duration, capacity, eventLocationId, " +
                "category, organizerId, `status` from `event` where organizerId=?";
        try {
            return jdbcTemplate.query(sql, new EventMapper(), organizerId);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    @Override
    public List<Event> findByKeyWord(String keyword){
        final String sql = "select eventId, title, `description`, event_date, duration, capacity, eventLocationId, " +
                "category, organizerId, `status` from `event` where (" +
                "title like '%?%' or `description` like '%?%' )";
        try {
            return jdbcTemplate.query(sql, new EventMapper(), keyword);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public Event add(Event event){
        final String sql = "insert into `event` (title, `description`, event_date, duration, capacity, eventLocationId," +
                "category, organizerId, `status`) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder= new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, event.getDate());
            ps.setInt(4, event.getDuration());
            ps.setInt(5,event.getCapacity());
            ps.setInt(6,event.getEventLocationId());
            ps.setString(7, event.getCategory());
            ps.setInt(8, event.getOrganizerId());
            ps.setBoolean(9,event.isStatus());
            return ps;

        }, keyHolder);
        if (rowsAffected <= 0){
            return null;
        }
        event.setId(keyHolder.getKey().intValue());
        return event;
    }
    @Override
    public boolean update(Event event){
            final String sql = "update event set " +
                    "title = ?, " +
                    "`description`=?, " +
                    "event_date = ?, " +
                    "duration = ?," +
                    "capacity= ?," +
                    "eventLocationId = ?, " +
                    "category = ?, " +
                    "organizerId = ?, " +
                    "`status` = ? ";
            return jdbcTemplate.update(sql,
                    event.getTitle(),
                    event.getDescription(),
                    event.getDate(),
                    event.getDuration(),
                    event.getCapacity(),
                    event.getEventLocationId(),
                    event.getCategory(),
                    event.getOrganizerId(),
                    event.isStatus()) > 0;
    }
    @Override
    public boolean deleteById(int eventId){
        jdbcTemplate.update("delete from app_user_event where eventId=?");
        int rowsAffected = jdbcTemplate.update("delete from event where eventId=?", eventId);

        return rowsAffected > 0;
    }



        //Work in progress

    private void addUser(Event event){
        final String sql = "select eu.app_user_id, eu.app_event_id, user.fname, user.lname, user.username" +
                "from app_user_event eu " +
                "inner join user u on eu.app_user_id = u.userId " +
                "where eu.app_event_id = ?";
        List<UserEvent> userEvents =jdbcTemplate.query(sql, new UserEventMapper(), event.getId());
        event.setAttendees(userEvents);
    }


}
