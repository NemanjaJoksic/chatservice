/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
@Component("authorizationProviderManager")
public class AuthorizationProviderManager {

    @Autowired
    private AuthorizationProvider authorizationProvider;
    
    public Authentication authorize(Authentication authentication) throws ChatServiceException {
        return authorizationProvider.authorize(authentication);
    }

}
