/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.mvc.Http;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Component("authenticationProviderManager")
public class AuthenticationProviderManager {

    @Autowired
    private List<AuthenticationProvider> authenticationProviders = new LinkedList<>();
    
    public Authentication authenticate(Http.RequestHeader header) throws ChatServiceException {
        if (authenticationProviders.isEmpty()) {
            throw ChatServiceException.generateException(ExceptionData.NOT_REGISTERED_AUTHENTICATED_PROVIDERS);
        }

        Authentication authentication = null;
        ChatServiceException lastException = null;
        for (AuthenticationProvider authenticationProvider : authenticationProviders) {
            try {
                authentication = authenticationProvider.authenticate(header);
                if(authentication != null)
                    break;
            } catch (ChatServiceException ex) {
                lastException = ex;
            }
        }
        
        if(authentication == null)
            throw lastException;
        
        return authentication;
    }

}
