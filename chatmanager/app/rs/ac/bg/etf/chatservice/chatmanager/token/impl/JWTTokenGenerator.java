/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.token.impl;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.chatmanager.config.Config;
import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.chatmanager.token.TokenGenerator;
import rs.ac.bg.etf.chatservice.commons.exception.GeneralException;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = "app.token-generator.type", havingValue = "jwt")
public class JWTTokenGenerator implements TokenGenerator {

    @Autowired
    private Config config;

    private SignatureAlgorithm signitureAlgorithm;
    
    @PostConstruct
    public void init() {
        signitureAlgorithm = SignatureAlgorithm.forName(config.getJwtSignitureAlgorithm());
    }
    
    @Override
    public Token generate(String userId, String channel) throws GeneralException {
        
        Date currentDate = new Date();
        Date expiringDate = DateUtils.addSeconds(currentDate, config.getJwtExpiresIn());
        
        JwtBuilder builder = Jwts.builder()
                .setSubject(userId)
                .setIssuer("CHAT_MANAGER")
                .setIssuedAt(currentDate)
                .setExpiration(expiringDate)
                .claim("channel", channel)
                .signWith(signitureAlgorithm, config.getJwtSigningKey());
        
        String value = builder.compact();
        return new Token(value, config.getJwtExpiresIn());
    }

}
