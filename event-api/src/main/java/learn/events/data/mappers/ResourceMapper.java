package learn.events.data.mappers;

import learn.events.models.Resources;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceMapper implements RowMapper<Resources> {


    @Override
    public Resources mapRow(ResultSet resultSet, int i) throws SQLException {
        Resources resource = new Resources();
        resource.setResourceId(resultSet.getInt("resourceId"));
        resource.setResource(resultSet.getString("resource"));
        resource.setLocationId(resultSet.getInt("location_id"));
        return resource;
    }
}
