/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest.client;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author joksin
 */
public abstract class ChatClient implements Runnable, InitializingBean {

    public static String[] clients;
    
    protected Random random = new Random();
    protected WebSocketSession wsSession;
    protected String token;
    protected String chatServerBaseUrl;
    
    public ChatClient(String chatServerBaseUrl, String token) {
        this.chatServerBaseUrl = chatServerBaseUrl;
        this.token = token;
    }
    
    @Override
    public void run() {
        try {
            String receiverId = clients[random.nextInt(clients.length)];
            wsSession.sendMessage(getMessage(receiverId));
        } catch (Exception ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        wsSession = connect(chatServerBaseUrl, token);
    }

    protected abstract WebSocketSession connect(String chatServerBaseUrl, String token) throws Exception;
    protected abstract AbstractWebSocketMessage getMessage(String receivedId) throws Exception;
    
}
