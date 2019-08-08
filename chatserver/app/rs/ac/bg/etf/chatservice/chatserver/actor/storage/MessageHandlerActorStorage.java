/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.storage;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import rs.ac.bg.etf.chatservice.chatserver.exception.ChatServerException;

/**
 *
 * @author joksin
 */
public interface MessageHandlerActorStorage {
    
    public CompletionStage<Void> put(String id, String actor) throws ChatServerException;
    
    public CompletionStage<Optional<String>> get(String id) throws ChatServerException;
    
    public CompletionStage<Void> remove(String id) throws ChatServerException;
    
}
