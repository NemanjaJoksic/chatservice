/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.service;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author joksin
 */
public interface ChatService {
    
    public CompletableFuture openConnection(String dataType, String messageType, String ticket);
    
}
