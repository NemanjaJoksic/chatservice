/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao;

import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
public interface ChannelDao {
    
    public String getChannelIdByUserId(String userId) throws ChatServiceException;
    
    public void createChannel(String userId, String channelId) throws ChatServiceException;
    
}
