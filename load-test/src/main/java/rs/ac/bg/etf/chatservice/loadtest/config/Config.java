/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.loadtest.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author joksin
 */
@Getter
@Configuration
public class Config {
    
    @Value("${loadtest.chatserver.urls}")
    private String[] chatServerUrls;
    
    @Value("${loadtest.client.text.json}")
    private Integer numberOfTextJsonClients;
    
}
