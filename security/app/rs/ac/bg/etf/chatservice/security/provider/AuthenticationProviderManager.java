/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;
import rs.ac.bg.etf.chatservice.security.exception.NotRegisteredAuthenticationProvidersException;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;

/**
 *
 * @author joksin
 */
@Component("authenticationProviderManager")
public class AuthenticationProviderManager {

    @Autowired
    private List<AuthenticationProvider> authenticationProviders = new LinkedList<>();
    
    public Authentication authenticate(Optional<String> optionalAuthrozationHeader) throws AuthenticationException {
        if (authenticationProviders.isEmpty()) {
            throw new NotRegisteredAuthenticationProvidersException();
        }

        Authentication authentication = null;
        SecurityException lastException = null;
        for (AuthenticationProvider authenticationProvider : authenticationProviders) {
            try {
                authentication = authenticationProvider.authenticate(optionalAuthrozationHeader);
                if(authentication != null)
                    break;
            } catch (SecurityException ex) {
                lastException = ex;
            }
        }
        
        if(authentication == null)
            throw lastException;
        
        return authentication;
    }

}
