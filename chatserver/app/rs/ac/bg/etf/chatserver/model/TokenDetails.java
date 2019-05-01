/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.model;

/**
 *
 * @author joksin
 */
public class TokenDetails {
    
    private String token;
    private String username;
    private String channel;
    private long timestamp;
    
    public TokenDetails() {
        
    }

    public TokenDetails(String token, String username, String channel, long timestamp) {
        this.token = token;
        this.username = username;
        this.channel = channel;
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}
