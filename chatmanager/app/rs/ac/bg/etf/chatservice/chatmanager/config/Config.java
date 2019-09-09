/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author joksin
 */
@Configuration
@Getter
public class Config {
    
    @Value("${chatmanager.chatserver.url}")
    private String[] chatServerUrls;
    
    @Value("${chatmanager.token-generator.jwt.algorithm}")
    private String jwtSignitureAlgorithm;
    
    @Value("${chatmanager.token-generator.jwt.signing-key}")
    private String jwtSigningKey;
        
    @Value("${chatmanager.token-generator.jwt.expires-in}")
    private int jwtExpiresIn;
    
}
