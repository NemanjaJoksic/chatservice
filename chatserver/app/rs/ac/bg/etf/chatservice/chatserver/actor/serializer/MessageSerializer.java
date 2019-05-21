/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.serializer;

import rs.ac.bg.etf.chatservice.chatserver.model.Messages;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
public interface MessageSerializer {
    
    public byte[] serialize(Messages.ChatMessage message) throws ChatServiceException;
    
    public Messages.ChatMessage deserialize(byte[] bytes) throws ChatServiceException;
    
}
