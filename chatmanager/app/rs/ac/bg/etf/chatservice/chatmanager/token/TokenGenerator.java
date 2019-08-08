/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.token;

import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.shared.exception.GeneralException;

/**
 *
 * @author joksin
 */
public interface TokenGenerator {
    
    public Token generate(String userId, String channel) throws GeneralException;
    
}
