/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest.util;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rs.ac.bg.etf.chatservice.loadtest.model.ProtobufMessages;

/**
 *
 * @author joksin
 */
public class ProtobufWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Opened new session in instance " + this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        byte[] bytes = message.getPayload().getBytes();
        ProtobufMessages.ChatMessage chatMessage = ProtobufMessages.ChatMessage.parseFrom(bytes);
        System.out.println(chatMessage.getSender() + "," + chatMessage.getReceiver() + "," + chatMessage.getMessage());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            byte[] bytes = message.getPayload().array();
            ProtobufMessages.ChatMessage chatMessage = ProtobufMessages.ChatMessage.parseFrom(bytes);
            System.out.println(chatMessage.getSender() + "," + chatMessage.getReceiver() + "," + chatMessage.getMessage());
        } catch (InvalidProtocolBufferException ex) {
            Logger.getLogger(ProtobufWebSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Session: " + session.getId() + "Connection closed");
        super.afterConnectionClosed(session, status); //To change body of generated methods, choose Tools | Templates.
    }

}
