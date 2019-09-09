/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
/**
 *
 * @author joksin
 */
//@Service
public class NoAuthorizationProvider implements AuthorizationProvider {

    @Override
    public Authentication authorize(Authentication authentication) throws AuthenticationException {
        return authentication;
    }
    
}
