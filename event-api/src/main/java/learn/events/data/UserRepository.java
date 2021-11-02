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

        if (user != null) {
            var authorities = getAuthorities(user.getUserId());
            user.setAuthorityNames(authorities);
        }
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
            statement.setString(3, user.getUserName());
            return statement;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());

        return user;

    }

    private void setAuthorities(User user) {

        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getUserId());

        for (var name : user.getAuthorityNames()) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "values (?, (select app_role_id from app_role where name = ?));";
            jdbcTemplate.update(sql, user.getUserId(), name);
        }
    }

    private List<String> getAuthorities(int appUserId) {

        String sql = "select `role`.roleId, `role`.title "
                + "from app_user_role "
                + "inner join `role` on app_role_id = roleId "
                + "where aur.app_user_id = ?";

        return jdbcTemplate.query(sql,
                (rs, i) -> rs.getString("name"),
                appUserId);
    }





}
