/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest.client;

import rs.ac.bg.etf.chatservice.loadtest.util.DataWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import rs.ac.bg.etf.chatservice.loadtest.model.ChatMessage;

/**
 *
 * @author joksin
 */
@Component
@Scope("prototype")
public class TextJsonChatClient extends ChatClient {

    private final ObjectMapper mapper = new ObjectMapper();
    
    public TextJsonChatClient(String chatServerBaseUrl, String token) {
        super(chatServerBaseUrl, token);
    }

    @Override
    protected WebSocketSession connect(String chatServerBaseUrl, String token) throws Exception {
        StandardWebSocketClient client = new StandardWebSocketClient();
        return client.doHandshake(new DataWebSocketHandler(), chatServerBaseUrl + "/chat/text/json/" + token).get();
    }

    @Override
    protected AbstractWebSocketMessage getMessage(String receivedId) throws Exception {
        ChatMessage message = new  ChatMessage();
        message.setReceiver(receivedId);
        message.setMessage("Message: " + UUID.randomUUID().toString());
        return new TextMessage(mapper.writeValueAsBytes(message));
    }
    
}
