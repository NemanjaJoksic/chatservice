/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.serializer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.stereotype.Component;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.JSON_SERIALIZATION;
import rs.ac.bg.etf.chatservice.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatservice.chatserver.model.Messages;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
@Component(JSON_SERIALIZATION)
public class JsonMessageSerializer implements MessageSerializer {

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public byte[] serialize(Messages.ChatMessage message) throws ChatServiceException {
        try {
            return mapper.writeValueAsBytes(message);
        } catch (JsonProcessingException ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    @Override
    public Messages.ChatMessage deserialize(byte[] bytes) throws ChatServiceException {
        try {
            return mapper.readValue(bytes, Messages.ChatMessage.class);
        } catch (IOException ex) {
            throw ChatServiceException.generateException(ex);
        }
    }
    
}
