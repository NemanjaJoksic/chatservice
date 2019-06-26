/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc;

import java.sql.ResultSet;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shareddb.AbstractJdbcDao;
import rs.ac.bg.etf.chatservice.chatmanager.dao.ChatDao;

/**
 *
 * @author joksin
 */
@Repository
public class JdbcChatlDao extends AbstractJdbcDao implements ChatDao {
    
    private static final String GET_CHANNEL_ID_BY_USER_ID = "SELECT * FROM PERSONAL_CHAT WHERE USER_ID = ?";
    
    private static final String INSERT_PERSONAL_CHAT = "INSERT INTO PERSONAL_CHAT(USER_ID,CHANNEL_ID) VALUES(?,?)";

    @Override
    public String getChannelIdByUserId(String userId) throws ChatServiceException {
        return jdbcTemplate.queryForObject(GET_CHANNEL_ID_BY_USER_ID, (int index, ResultSet rs) -> rs.getString("CHANNEL_ID"), userId);
    }

    @Override
    public void createPersonalChat(String userId, String channelId) throws ChatServiceException {
        jdbcTemplate.update(INSERT_PERSONAL_CHAT, userId, channelId);
    }
    
}
