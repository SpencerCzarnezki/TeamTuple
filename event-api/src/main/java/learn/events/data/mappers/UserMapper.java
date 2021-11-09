package learn.events.data.mappers;

import learn.events.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setFname(rs.getString("fname"));
        user.setLname(rs.getString("lname"));
        user.setUserName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password_hash"));
        user.setDisabled(rs.getBoolean("disabled"));
        return user;
    }
}
