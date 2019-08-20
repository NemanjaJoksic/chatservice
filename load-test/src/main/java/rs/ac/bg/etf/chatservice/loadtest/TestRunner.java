/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.loadtest.client.ChatClient;
import rs.ac.bg.etf.chatservice.loadtest.client.TextJsonChatClient;
import rs.ac.bg.etf.chatservice.loadtest.config.Config;

/**
 *
 * @author joksin
 */
@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private Config config;
    
    @Override
    public void run(String... args) throws Exception {
        
        int numberOfTextJsonClients = config.getNumberOfTextJsonClients();
        ChatClient.clients = new String[numberOfTextJsonClients];
        for(int i = 0; i < numberOfTextJsonClients; i++) {
            ChatClient.clients[i] = "CHANNEL_ID_" + i;
        }
        
        String[] chatServerUrls = config.getChatServerUrls();
        int i = 0, currentUrlIndex = 0, urlsNum = chatServerUrls.length;
        Random random = new Random();
        for(String token : getTokens()) {
            ChatClient chatClient = beanFactory.getBean(TextJsonChatClient.class, chatServerUrls[currentUrlIndex], token);
            taskScheduler.scheduleAtFixedRate(chatClient, Duration.ofSeconds(random.nextInt(3) + 3));
            currentUrlIndex = (currentUrlIndex + 1) % urlsNum;
            if(i++ == numberOfTextJsonClients)
                break;
        }
        
    }

    private List<String> getTokens() throws IOException {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("tokens.txt"))) {
            String token;
            while ((token = br.readLine()) != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}
