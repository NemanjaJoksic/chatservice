/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import play.mvc.Http;
import rs.ac.bg.etf.chatservice.security.crypto.PasswordEncoder;
import rs.ac.bg.etf.chatservice.security.exception.InvalidAuthorizationHeaderException;
import rs.ac.bg.etf.chatservice.security.exception.UserNotFoundException;
import rs.ac.bg.etf.chatservice.security.exception.WrongPasswordException;
import rs.ac.bg.etf.chatservice.security.model.authentication.AnonymousAuthentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.RoleBasedAuthentication;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;
import rs.ac.bg.etf.chatservice.security.service.UserDetailsService;

/**
 *
 * @author joksin
 */
@Service
@ConditionalOnExpression("${security.authentication.basic.enabled}")
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Optional<String> optionalAuthrozationHeader) throws SecurityException {
        
        if(!optionalAuthrozationHeader.isPresent())
            return new AnonymousAuthentication();
        
        String authorizationHeader = optionalAuthrozationHeader.get();
        String[] credentials = extractCredentialsFromAuthorizationHeader(authorizationHeader);
        Optional<UserDetails> optionalUserDetails = userDetailsService.getUserByUsername(credentials[0]);
        UserDetails userDetails = optionalUserDetails.orElseThrow(() -> new UserNotFoundException());
        
        if(!passwordEncoder.matches(credentials[1], userDetails.getPassword())) {
            throw new WrongPasswordException();
        }
        
        return new RoleBasedAuthentication(userDetails.getUsername(), userDetails.getAuthorities());
    }
    
    private String[] extractCredentialsFromAuthorizationHeader(String authorization) throws SecurityException {

        if (!authorization.toLowerCase().startsWith("basic")) {
            throw new InvalidAuthorizationHeaderException();
        }

        String[] split = authorization.split(" ");
        if (split.length != 2) {
            throw new InvalidAuthorizationHeaderException();
        }

        byte[] credDecoded = Base64.getDecoder().decode(split[1]);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        return credentials.split(":");

    }
    
}
