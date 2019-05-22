/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service;

import java.util.concurrent.CompletionStage;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.chatmanager.model.Connect;

/**
 *
 * @author joksin
 */
@Service
public class ChatService {
    
    public CompletionStage<Connect> connect(String user, String dataType, String messageType) {
        return null;
    }
    
}
