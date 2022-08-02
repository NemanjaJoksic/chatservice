/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.storage;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import rs.ac.bg.etf.chatservice.chatserver.exception.ChatServerException;

/**
 *
 * @author joksin
 */
public interface MessageHandlerActorStorage {
    
    public CompletableFuture<Void> put(String id, String actor) throws ChatServerException;
    
    public CompletableFuture<Optional<String>> get(String id) throws ChatServerException;
    
    public CompletableFuture<Void> remove(String id) throws ChatServerException;
    
}
