/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.token.auth;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.chatserver.exception.ChatServerException;
import rs.ac.bg.etf.chatservice.chatserver.exception.InvalidTokenException;
import rs.ac.bg.etf.chatservice.chatserver.exception.TokenExpiredException;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.chatserver.token.storage.TokenStore;

/**
 *
 * @author joksin
 */
@Component
//@ConditionalOnProperty(name = "app.token.authentication.type", havingValue = STORAGE_TICKET_AUTHENTICATOR)
public class TokenAuthenticator {

    @Autowired
    private TokenStore tokenStore;

    public TokenDetails validateToken(String token) throws ChatServerException {
        TokenDetails details = tokenStore.get(token);
        
        if(details == null)
            throw new InvalidTokenException(token);
        
        if(details.getTimestamp() < System.currentTimeMillis())
            throw new TokenExpiredException(token);
        
        tokenStore.remove(token);
        return details;
    }

}
