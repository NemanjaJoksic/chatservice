/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import rs.ac.bg.etf.chatservice.chatmanager.model.Chat;
import rs.ac.bg.etf.chatservice.data.jdbc.RowMapper;

/**
 *
 * @author joksin
 */
public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(int index, ResultSet rs) throws SQLException {
        Chat chat = new Chat();
        chat.setChatId(rs.getString("CHAT_ID"));
        chat.setChannelId(rs.getString("CHANNEL_ID"));
        chat.setChannelName(rs.getString("CHANNEL_NAME"));
        chat.setChannelType(rs.getString("CHANNEL_TYPE"));
        return chat;
    }
    
}
