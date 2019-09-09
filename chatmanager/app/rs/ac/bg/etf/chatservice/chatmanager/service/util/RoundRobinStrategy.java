/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.chatmanager.config.Config;

/**
 *
 * @author joksin
 */
@Component
public class RoundRobinStrategy implements Strategy {

    @Autowired
    private Config config;
    
    private AtomicInteger next;
    private String[] urls;
    
    @PostConstruct
    public void init() {
        this.next = new AtomicInteger();
        this.urls = config.getChatServerUrls();
    }
    
    @Override
    public String getChatServerUrl() {
        int index = next.incrementAndGet() % urls.length;
        return urls[index];
    }
    
}
