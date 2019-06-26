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
import org.springframework.stereotype.Service;
import play.mvc.Http;
import rs.ac.bg.etf.chatservice.security.crypto.PasswordEncoder;
import rs.ac.bg.etf.chatservice.security.model.authentication.AnonymousAuthentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.RoleBasedAuthentication;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;
import rs.ac.bg.etf.chatservice.security.service.UserDetailsService;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Service
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Optional<String> optionalAuthrozationHeader) throws ChatServiceException {
        
        if(!optionalAuthrozationHeader.isPresent())
            return new AnonymousAuthentication();
        
        String authorizationHeader = optionalAuthrozationHeader.get();
        String[] credentials = extractCredentialsFromAuthorizationHeader(authorizationHeader);
        Optional<UserDetails> optionalUserDetails = userDetailsService.getUserByUsername(credentials[0]);
        UserDetails userDetails = optionalUserDetails.orElseThrow(() -> ChatServiceException.generateException(ExceptionData.USER_NOT_FOUNT));
        
        if(!passwordEncoder.matches(credentials[1], userDetails.getPassword())) {
            throw ChatServiceException.generateException(ExceptionData.WRONG_PASSWORD);
        }
        
        return new RoleBasedAuthentication(userDetails.getUsername(), userDetails.getAuthorities());
    }
    
    private String[] extractCredentialsFromAuthorizationHeader(String authorization) throws ChatServiceException {

        if (!authorization.toLowerCase().startsWith("basic")) {
            throw ChatServiceException.generateException(ExceptionData.INVALID_AUTHORIZATION_HEADER);
        }

        String[] split = authorization.split(" ");
        if (split.length != 2) {
            throw ChatServiceException.generateException(ExceptionData.INVALID_AUTHORIZATION_HEADER);
        }

        byte[] credDecoded = Base64.getDecoder().decode(split[1]);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        return credentials.split(":");

    }
    
}
