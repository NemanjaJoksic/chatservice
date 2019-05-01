/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.token.storage;

import rs.ac.bg.etf.chatserver.model.TokenDetails;

/**
 *
 * @author joksin
 */
public interface TokenStore {
    
    public void put(String ticket, TokenDetails details);
    
    public TokenDetails get(String ticket);
    
    public void remove(String ticket);
    
}
