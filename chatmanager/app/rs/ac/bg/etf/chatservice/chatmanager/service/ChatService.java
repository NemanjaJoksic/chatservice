/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service;

import akka.actor.ActorSystem;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.chatmanager.config.Config;
import rs.ac.bg.etf.chatservice.chatmanager.executor.ServiceExecutionContext;
import rs.ac.bg.etf.chatservice.chatmanager.model.Connect;
import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.chatmanager.token.TokenGenerator;
import rs.ac.bg.etf.chatservice.chatmanager.dao.ChatDao;
import rs.ac.bg.etf.chatservice.chatmanager.model.Chat;

/**
 *
 * @author joksin
 */
@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    @Autowired
    private ActorSystem actorSystem;
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    @Autowired
    private Config config;

    @Autowired
    private ChatDao chatDao;
    
    private ServiceExecutionContext executionContext;
    
    @PostConstruct
    public void init() {
        executionContext = new ServiceExecutionContext(actorSystem);
    }
    
    public CompletionStage<List<Chat>> getAllChats() {
        return CompletableFuture.supplyAsync(() -> {
            return chatDao.getAllChats();
        });
    }
    
    public CompletionStage<Connect> connect(String userId, String dataType, String messageType) {
        return CompletableFuture.supplyAsync(() -> {
            
            Chat chat = chatDao.getChatByUserId(userId);
            if(chat == null) {
                String channelId = UUID.randomUUID().toString();
                chat = new Chat(userId, channelId, userId, "PERSONAL");
                chatDao.createChat(chat);
                logger.info("New channel created [user_id -> {}, channel_id -> {}]", userId, channelId);
            }
            
            Token token = tokenGenerator.generate(userId, chat);
            logger.info("Token generated {}", token.getValue());
            logger.info("User connected [user_id -> {}, channel_id -> {}]", userId, chat.getChannelId());
            
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
                .append("/").append(dataType)
                .append("/").append(messageType)
                .append("/").append(token);
        return sb.toString();
    }

}
