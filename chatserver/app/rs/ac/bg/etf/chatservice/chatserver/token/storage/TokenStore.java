/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.token.storage;

import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
public interface TokenStore {
    
    public void put(String ticket, TokenDetails details) throws ChatServiceException;
    
    public TokenDetails get(String token) throws ChatServiceException;
    
    public void remove(String token) throws ChatServiceException;
    
}
