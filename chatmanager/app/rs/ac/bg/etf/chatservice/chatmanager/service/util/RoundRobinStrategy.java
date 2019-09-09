/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service.util;

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
    
    private int next;
    private String[] urls;
    
    @PostConstruct
    public void init() {
        this.next = 0;
        this.urls = config.getChatServerUrls();
    }
    
    @Override
    public synchronized String getChatServerUrl() {
        String url = urls[next];
        next = (next + 1) % urls.length;
        return url;
    }
    
}
