/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service;

import akka.actor.ActorSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.chatmanager.config.Config;
import rs.ac.bg.etf.chatservice.chatmanager.executor.ServiceExecutionContext;
import rs.ac.bg.etf.chatservice.chatmanager.model.Connect;
import rs.ac.bg.etf.chatservice.chatmanager.model.Register;
import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.chatmanager.token.TokenGenerator;

/**
 *
 * @author joksin
 */
@Service
public class ChatService {

    @Autowired
    private ActorSystem actorSystem;
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    @Autowired
    private Config config;

    private ServiceExecutionContext executionContext;
    
    @PostConstruct
    public void init() {
        executionContext = new ServiceExecutionContext(actorSystem);
    }
    
    public CompletionStage<Register> register() {
        return CompletableFuture.supplyAsync(() -> {
            return new Register();
        }, executionContext);
    }
    
    public CompletionStage<Connect> connect(String userId, String dataType, String messageType) {
        return CompletableFuture.supplyAsync(() -> {
            Token token = tokenGenerator.generate(userId, null);
            String chatServerUrl = generateChatServerUrl(token.getValue(), dataType, messageType);
            Connect connect = new Connect();
            connect.setChatServerUrl(chatServerUrl);
            connect.setDataType(dataType);
            connect.setMessageType(messageType);
            connect.setUserId(userId);
            connect.setExpiresIn(token.getExpiresIn());
            return connect;
        }, executionContext);
    }

    private String generateChatServerUrl(String token, String dataType, String messageType) {
        StringBuilder sb = new StringBuilder();
        sb.append(config.getChatServerUrl().replaceAll("\"", ""))
                .append("/").append(token)
                .append("/").append(dataType)
                .append("/").append(messageType);
        return sb.toString();
    }

}
