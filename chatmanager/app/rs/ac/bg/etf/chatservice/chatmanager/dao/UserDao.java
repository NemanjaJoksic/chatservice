/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao;

import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.commons.exception.GeneralException;

/**
 *
 * @author joksin
 */
public interface UserDao {
    
    public void createUser(User user) throws GeneralException;
    
    public User getUser(String username) throws GeneralException;
    
}
