package learn.events.data;


import learn.events.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> mapper =(rs,i)->{
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setFname(rs.getString("fname"));
        user.setLname(rs.getString("lname"));
        user.setUserName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setDisabled(rs.getBoolean("disabled"));
        return user;
    };


    public UserRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    public List<User> findAll(){
        return jdbcTemplate.query("select userId, fname, lname, username,email,password_hash,disabled, " +
                "from `user`;",mapper);
    }


    public List<String> findAllRoles() {
        return jdbcTemplate.query("select * from `role`;",
                (rs, i) -> rs.getString("title"));
    }

    public User findByUsername(String username){
        User user = jdbcTemplate.query("select * from `user` where username = ?;",
                mapper,
                username).stream()
                .findFirst()
                .orElse(null);
        return user;
    }

    public User findByUserId(int id) {
        User user = jdbcTemplate.query(
                        "select * from `user` where userId = ?;",
                        mapper,
                        id).stream()
                .findFirst()
                .orElse(null);

        return user;

    }


    public User add(User user){

        final String sql = "insert into `user` (fname, lname, username, email, password_hash) values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            return statement;
        }, keyHolder);
    }




}
