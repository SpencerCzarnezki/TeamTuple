package learn.events.data;


import learn.events.data.mappers.UserMapper;
import learn.events.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;




    public UserRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    @Override
    public List<User> findAll(){
        return jdbcTemplate.query("select userId, fname, lname, username,email,password_hash,disabled " +
                "from `user`;", new UserMapper());
    }


    @Override
    public List<String> findAllRoles() {
        return jdbcTemplate.query("select * from `role`;",
                (rs, i) -> rs.getString("title"));
    }

    @Override
    @Transactional
    public User findByUsername(String username){
        User user = jdbcTemplate.query("select * from `user` where username = ?;",
                new UserMapper(),
                username).stream()
                .findFirst()
                .orElse(null);

        if (user != null) {
            var authorities = getAuthorities(user.getUserId());
            user.setAuthorityNames(authorities);
        }
        return user;
    }

    @Override
    public User findByUserId(int id) {
        User user = jdbcTemplate.query(
                        "select fname, lname, username from `user` where userId = ?;",
                        mapper,
                        id).stream()
                .findFirst()
                .orElse(null);

        return user;

    }
    private final RowMapper<User> mapper =(resultSet, i) -> {
        User user = new User();
        user.setFname(resultSet.getString("fname"));
        user.setLname(resultSet.getString("lname"));
        user.setUserName(resultSet.getString("username"));
        return user;
    };


    @Override
    public User add(User user){

        final String sql = "insert into `user` (fname, lname, username, email, password_hash, disabled) values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,user.getFname());
            statement.setString(2, user.getLname());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setString(5,user.getPassword());
            statement.setBoolean(6,!user.isEnabled());
            return statement;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());

        addRole(user);

        return user;

    }
    private void addRole(User user) {
        String sql2 = "insert into app_user_role (app_user_id, app_role_id) "
                + "values (?, (select roleId from `role` where title = ?));";
        jdbcTemplate.update(sql2, user.getUserId(), "USER");
    }

    @Override
    public boolean update(User user) {

        String sql = "update `user` set "
                + "fname =?, "
                + "lname=?, "
                + "username = ?, "
                + "email=?, "
                + "disabled = ? "
                + "where userId = ?;";

        int rowsAffected = jdbcTemplate.update(sql,
                user.getFname(),
                user.getLname(),
                user.getUsername(),
                user.getEmail(),
                !user.isEnabled(),
                user.getUserId());

        if (rowsAffected > 0) {
            setAuthorities(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean changePassword(User user) {

        String sql = "update `user` set "
                + "password_hash = ? "
                + "where userId = ?;";

        int rowsAffected = jdbcTemplate.update(sql,
                user.getPassword(),
                user.getUserId());

        return rowsAffected > 0;
    }



    private void setAuthorities(User user) {

        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getUserId());

        for (var name : user.getAuthorityNames()) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "values (?, (select roleId from `role` where title = ?));";
            jdbcTemplate.update(sql, user.getUserId(), name);
        }
    }

    private List<String> getAuthorities(int appUserId) {

        String sql = "select  roleId,  title "
                + "from app_user_role "
                + "inner join `role` on app_role_id = roleId "
                + "where app_user_id = ?";

        return jdbcTemplate.query(sql,
                (rs, i) -> rs.getString("title"),
                appUserId);
    }





}
