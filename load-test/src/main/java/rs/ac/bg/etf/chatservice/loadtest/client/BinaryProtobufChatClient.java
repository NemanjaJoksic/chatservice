/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import rs.ac.bg.etf.chatservice.loadtest.model.ProtobufMessages;
import rs.ac.bg.etf.chatservice.loadtest.util.JsonWebSocketHandler;

/**
 *
 * @author joksin
 */
@Component
@Scope("prototype")
public class BinaryProtobufChatClient extends ChatClient {

    private final ObjectMapper mapper = new ObjectMapper();

    public BinaryProtobufChatClient(String chatServerBaseUrl, String token) {
        super(chatServerBaseUrl, token);
    }

    @Override
    protected WebSocketSession connect(String chatServerBaseUrl, String token) throws Exception {
        StandardWebSocketClient client = new StandardWebSocketClient();
        return client.doHandshake(new JsonWebSocketHandler(), chatServerBaseUrl + "/chat/binary/protobuf/" + token).get();
    }

    @Override
    protected AbstractWebSocketMessage getMessage(String receiverId) throws Exception {
        ProtobufMessages.ChatMessage message = ProtobufMessages.ChatMessage.newBuilder()
                .setReceiver(receiverId)
                .setMessage("Message: " + UUID.randomUUID().toString()).build();
        return new BinaryMessage(message.toByteArray());
    }

}
