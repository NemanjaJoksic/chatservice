/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.token.auth.impl;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.chatserver.token.auth.TokenAuthenticator;
import rs.ac.bg.etf.chatservice.chatserver.token.storage.TokenStore;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Component
//@ConditionalOnProperty(name = "app.token.authentication.type", havingValue = STORAGE_TICKET_AUTHENTICATOR)
public class TokenAuthenticatorImpl implements TokenAuthenticator {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public TokenDetails validateToken(String ticket) throws ChatServiceException {
        TokenDetails details = tokenStore.get(ticket);
        
        if(details == null)
            throw ChatServiceException.generateException(ExceptionData.INVALID_TICKET, Arrays.asList(ticket));
        
        if(details.getTimestamp() < System.currentTimeMillis())
            throw ChatServiceException.generateException(ExceptionData.TICKET_EXPIRED, Arrays.asList(ticket));
        
        tokenStore.remove(ticket);
        return details;
    }

}
