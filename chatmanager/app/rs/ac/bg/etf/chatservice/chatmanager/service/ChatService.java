/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service;

import akka.actor.ActorSystem;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.chatmanager.config.Config;
import rs.ac.bg.etf.chatservice.chatmanager.dao.ChannelDao;
import rs.ac.bg.etf.chatservice.chatmanager.executor.ServiceExecutionContext;
import rs.ac.bg.etf.chatservice.chatmanager.model.Connect;
import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.chatmanager.token.TokenGenerator;

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
    private ChannelDao channelDao;
    
    private ServiceExecutionContext executionContext;
    
    @PostConstruct
    public void init() {
        executionContext = new ServiceExecutionContext(actorSystem);
    }
    
    public CompletionStage<Connect> connect(String userId, String dataType, String messageType) {
        return CompletableFuture.supplyAsync(() -> {
            
            String channel = channelDao.getChannelIdByUserId(userId);
            if(channel == null) {
                channel = UUID.randomUUID().toString();
                channelDao.createChannel(userId, channel);
                logger.info("New channel created [user_id -> {}, channel_id -> {}]", userId, channel);
            }
            
            Token token = tokenGenerator.generate(userId, channel);
            logger.info("Token generated {}", token.getValue());
            logger.info("User connected [user_id -> {}, channel_id -> {}]", userId, channel);
            
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
