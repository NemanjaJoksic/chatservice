/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.token.impl;

import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.chatmanager.model.Token;
import rs.ac.bg.etf.chatservice.chatmanager.token.TokenGenerator;
import rs.ac.bg.etf.chatservice.shared.exception.GeneralException;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = "app.token-generator.type", havingValue = "uuid")
public class UUIDTokenGenerator implements TokenGenerator {

    @Override
    public Token generate(String userId, String channel) throws GeneralException {
        String uuid = UUID.randomUUID().toString();
        int expiresIn = 300;
        return new Token(uuid, expiresIn);
    }
    
}
