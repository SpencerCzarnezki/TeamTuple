package learn.events.data.mappers;

import learn.events.models.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location location = new Location();
        location.setId(resultSet.getInt("locationId"));
        location.setTitle(resultSet.getString("title"));
        location.setCity(resultSet.getString("city"));
        location.setAddress(resultSet.getString("address"));
        location.setZipcode(resultSet.getInt("zipcode"));
        location.setState(resultSet.getString("state"));
        return location;
    }
}
