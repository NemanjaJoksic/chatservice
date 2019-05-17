/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.serializer;

import rs.ac.bg.etf.chatservice.chatserver.exception.ChatServerException;
import rs.ac.bg.etf.chatservice.chatserver.model.Messages;

/**
 *
 * @author joksin
 */
public interface MessageSerializer {
    
    public byte[] serialize(Messages.ChatMessage message) throws ChatServerException;
    
    public Messages.ChatMessage deserialize(byte[] bytes) throws ChatServerException;
    
}
