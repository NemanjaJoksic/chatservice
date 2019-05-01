/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.token.auth.impl;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.model.TokenDetails;
import static rs.ac.bg.etf.chatserver.Consts.STORAGE_TICKET_AUTHENTICATOR;
import rs.ac.bg.etf.chatserver.exception.ChatServerException;
import rs.ac.bg.etf.chatserver.exception.ExceptionData;
import rs.ac.bg.etf.chatserver.token.auth.TokenAuthenticator;
import rs.ac.bg.etf.chatserver.token.storage.TokenStore;

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
    public TokenDetails validateToken(String ticket) throws ChatServerException {
        TokenDetails details = tokenStore.get(ticket);
        
        if(details == null)
            throw ChatServerException.generateException(ExceptionData.INVALID_TICKET, Arrays.asList(ticket));
        
        if(details.getTimestamp() < System.currentTimeMillis())
            throw ChatServerException.generateException(ExceptionData.TICKET_EXPIRED, Arrays.asList(ticket));
        
//        tokenStore.remove(ticket);
        return details;
    }

}
