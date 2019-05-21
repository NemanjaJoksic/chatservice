/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.serializer.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.stereotype.Component;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.PROTOBUF_SERIALIZATION;
import rs.ac.bg.etf.chatservice.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatservice.chatserver.model.Messages;
import rs.ac.bg.etf.chatservice.chatserver.model.ProtobufMessages;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
@Component(PROTOBUF_SERIALIZATION)
public class ProtobufMessageSerializer implements MessageSerializer {

    @Override
    public byte[] serialize(Messages.ChatMessage message) throws ChatServiceException {
        ProtobufMessages.ChatMessage.Builder builder = ProtobufMessages.ChatMessage.newBuilder();
        builder.setSender(message.getSender());
        builder.setReceiver(message.getReceiver());
        builder.setMessage(message.getMessage());
        return builder.build().toByteArray();
    }

    @Override
    public Messages.ChatMessage deserialize(byte[] bytes) throws ChatServiceException {
        try {
            ProtobufMessages.ChatMessage messageInfo = ProtobufMessages.ChatMessage.parseFrom(bytes);
            Messages.ChatMessage message = new Messages.ChatMessage();
            message.setSender(messageInfo.getSender());
            message.setReceiver(messageInfo.getReceiver());
            message.setMessage(messageInfo.getMessage());
            return message;
        } catch (InvalidProtocolBufferException ex) {
            throw ChatServiceException.generateException(ex);
        }
    }
    
}
