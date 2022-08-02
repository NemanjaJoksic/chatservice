/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.chatmanager.dao.ChatDao;
import rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc.mapper.ChatMapper;
import rs.ac.bg.etf.chatservice.chatmanager.model.Chat;
import rs.ac.bg.etf.chatservice.commons.exception.GeneralException;
import rs.ac.bg.etf.chatservice.data.jdbc.AbstractJdbcDao;

/**
 *
 * @author joksin
 */
@Repository
public class JdbcChatlDao extends AbstractJdbcDao implements ChatDao {
    
    private static final String GET_ALL_CHATS = "SELECT * FROM CHAT";
    
    private static final String GET_CHAT_BY_USER_ID = "SELECT * FROM CHAT WHERE CHAT_ID = ?";
    
    private static final String INSERT_PERSONAL_CHAT = "INSERT INTO CHAT(CHAT_ID,CHANNEL_ID,CHANNEL_NAME,CHANNEL_TYPE) VALUES(?,?,?,?)";

    @Override
    public List<Chat> getAllChats() throws GeneralException {
        return jdbcTemplate.queryForList(GET_ALL_CHATS, new ChatMapper());
    }
    
    @Override
    public Chat getChatByUserId(String userId) throws GeneralException {
        return jdbcTemplate.queryForObject(GET_CHAT_BY_USER_ID, new ChatMapper(), userId);
    }

    @Override
    public void createChat(Chat chat) throws GeneralException {
        jdbcTemplate.update(INSERT_PERSONAL_CHAT, chat.getChatId(), chat.getChannelId(), 
                chat.getChannelName(), chat.getChannelType());
    }

}
