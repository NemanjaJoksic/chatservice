/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.model;

import lombok.Getter;

/**
 *
 * @author joksin
 */
@Getter
public class TokenDetails {
    
    private String token;
    private String userId;
    private String channel;
    private long timestamp;
    
    public TokenDetails() {
        
    }

    public TokenDetails(String token, String userId, String channel, long timestamp) {
        this.token = token;
        this.userId = userId;
        this.channel = channel;
        this.timestamp = timestamp;
    }
    
}
