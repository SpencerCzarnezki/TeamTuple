package learn.events.data;

import learn.events.data.mappers.LocationMapper;
import learn.events.models.Location;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocationMySQLRepository implements LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationMySQLRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Location> findAll() throws DataAccessException {
        final String sql = "select * from location;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public Location findById(int id) throws DataAccessException {
        final String sql = "select * from location where locationId = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> findByCity(String city) throws DataAccessException {
        final String sql = "select * from location where city = ?;";
        try {
            return jdbcTemplate.query(sql, new LocationMapper(), city);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Location add(Location location) throws DataAccessException {
        final String sql = "insert into location"
                + "(title, city, address, zipcode, state) "
                + "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, location.getTitle());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getAddress());
            statement.setInt(4, location.getZipcode());
            statement.setString(5, location.getState());
            return statement;
        }, keyHolder);

        if (rowsAffected <= 0) {
            throw new DataAccessException("location insert failed", null);
        }

        location.setId(keyHolder.getKey().intValue());

        return location;
    }

    @Override
    public boolean update(Location location) throws DataAccessException {

        final String sql = "update location set "
                + "title = ?, "
                + "city = ?, "
                + "address = ?, "
                + "zipcode = ?, "
                + "state = ?"
                + "where locationId = ?;";

        int rowsAffected = jdbcTemplate.update(sql,
                location.getTitle(),
                location.getCity(),
                location.getAddress(),
                location.getZipcode(),
                location.getState(),
                location.getId());

        return rowsAffected > 0;
    }

}
