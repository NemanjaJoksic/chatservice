/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao;

import rs.ac.bg.etf.chatservice.chatmanager.model.Chat;
import rs.ac.bg.etf.chatservice.commons.exception.GeneralException;

/**
 *
 * @author joksin
 */
public interface ChatDao {
    
    public Chat getChatByUserId(String userId) throws GeneralException;
    
    public void createChat(Chat chat) throws GeneralException;
    
}
