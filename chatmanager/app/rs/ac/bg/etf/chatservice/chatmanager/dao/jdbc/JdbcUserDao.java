/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc;

import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.chatmanager.dao.UserDao;
import rs.ac.bg.etf.chatservice.security.model.authentication.SimpleAuthority;
import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shareddb.AbstractJdbcDao;

/**
 *
 * @author joksin
 */
@Repository
public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
    
    private static final String INSERT_USER = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";
    
    private static final String INSERT_ROLES = "INSERT INTO ROLES(USERNAME,ROLE_NAME) VALUES(?,?)";
    
    private static final String GET_USER_BY_USERNAME
            = "SELECT * FROM USERS u\n"
            + "LEFT JOIN ROLES r ON u.USERNAME = r.USERNAME\n"
            + "WHERE u.USERNAME = ?";
    
    @Override
    public void createUser(User user) throws ChatServiceException {
        user.getAuthorities().forEach(authority -> jdbcTemplate.update(INSERT_ROLES, user.getUsername(), authority.getAuthority()));
        jdbcTemplate.update(INSERT_USER, user.getUsername(), user.getPassword());
    }

    @Override
    public User getUser(String username) throws ChatServiceException {
        AtomicBoolean found = new AtomicBoolean(false);
        User user = new User();
        jdbcTemplate.queryForList(GET_USER_BY_USERNAME, (int index, ResultSet rs) -> {
            if(user.getUsername() == null || user.getUsername().isEmpty()) {
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                found.getAndSet(true);
            }
            String role = rs.getString("ROLE_NAME");
            if(role != null && !role.isEmpty())
                user.getAuthorities().add(new SimpleAuthority(role));
            return null;
        }, username);
        return found.get() ? user : null;
    }
    
}
