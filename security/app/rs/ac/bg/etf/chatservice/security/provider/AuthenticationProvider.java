/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import java.util.Optional;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;

/**
 *
 * @author joksin
 */
public interface AuthenticationProvider {
    
    public Authentication authenticate(Optional<String> optionalAuthrozationHeader) throws AuthenticationException;
    
}
