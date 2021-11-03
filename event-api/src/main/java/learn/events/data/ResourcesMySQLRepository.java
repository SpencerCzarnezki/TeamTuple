package learn.events.data;

import learn.events.data.mappers.ResourceMapper;
import learn.events.models.Resources;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ResourcesMySQLRepository implements ResourcesRepository{

    private final JdbcTemplate jdbcTemplate;

    public ResourcesMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Resources> findAll() {
        final String sql = "select * from resources";
        return jdbcTemplate.query(sql, new ResourceMapper());
    }

    @Override
    public List<Resources> findByDescription(String description) {
        final String sql = "select resourceId, `resource`, location_id "
                + "from `resources` where resource = ?;";

        try {
            return jdbcTemplate.query(sql, new ResourceMapper(), description);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Resources findById(int id) {
        final String sql = "select * from resources where resourceId = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, new ResourceMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Resources add(Resources resource) {
        final String sql = "insert into resources"
                + "(resource, location_id) "
                + "values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, resource.getResource());
            statement.setInt(2, resource.getLocationId());
            return statement;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        resource.setResourceId(keyHolder.getKey().intValue());

        return resource;
    }

    @Override
    public boolean deleteById(int resourceId) {
        int rowsAffected = jdbcTemplate.update("delete from resources where resourceId = ?", resourceId);

        return rowsAffected > 0;
    }
}
