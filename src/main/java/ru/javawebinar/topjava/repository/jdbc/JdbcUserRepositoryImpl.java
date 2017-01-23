package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */
//http://wideskills.com/spring/transaction-management-in-spring
@Transactional(readOnly = true)
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
        try {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", user.getId())
                    .addValue("name", user.getName())
                    .addValue("email", user.getEmail())
                    .addValue("password", user.getPassword())
                    .addValue("registered", user.getRegistered())
                    .addValue("enabled", user.isEnabled())
                    .addValue("caloriesPerDay", user.getCaloriesPerDay())
                    .addValue("roles", user.getRoles());

            if (user.isNew()) {
                Number newKey = insertUser.executeAndReturnKey(map);
                user.setId(newKey.intValue());
                createRoles(user);

            } else {
                namedParameterJdbcTemplate.update(
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
                updateRoles(user);
            }
            dataSourceTransactionManager.commit(status);
            return user;

        } catch (DataAccessException e) {
            dataSourceTransactionManager.rollback(status);
           throw e;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        try {
            return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
       User user = DataAccessUtils.singleResult(users);
            getRoles(user);
        return  user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        getRoles(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        for (User user:users) {getRoles(user);}
        return users;
    }

    public void insertBatchSQL(final String sql){

        jdbcTemplate.batchUpdate(new String[]{sql});

    }

    private void updateRoles(User user)
    {
        for (Role role:user.getRoles())
        {
            jdbcTemplate.update("UPDATE user_roles SET role=? WHERE user_id=?" ,role.toString(), user.getId());
        }
    }

    private void createRoles(User user)
    {
        for (Role role:user.getRoles())
        {
            jdbcTemplate.update("INSERT INTO user_roles (user_id, role) values(?,?)" ,user.getId(), role.toString());
        }
    }
    private void getRoles(User user) {
        List<Role> roles = null;
        try {
            roles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?", new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Role.valueOf(resultSet.getString("role"));
                    }
                }, user.getId());
        } catch (NullPointerException e) {
            return;
        }
        user.setRoles(new HashSet<Role>(roles));}
}
