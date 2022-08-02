/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import java.util.Optional;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;
import rs.ac.bg.etf.chatservice.security.exception.InvalidAuthorizationHeaderException;
import rs.ac.bg.etf.chatservice.security.model.authentication.AnonymousAuthentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.RoleBasedAuthentication;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;

/**
 *
 * @author joksin
 */
public abstract class TokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Optional<String> optionalAuthrozationHeader) throws AuthenticationException {
        if (!optionalAuthrozationHeader.isPresent())
            return new AnonymousAuthentication();

        String authorizationHeader = optionalAuthrozationHeader.get();

        if (!authorizationHeader.toLowerCase().startsWith("bearer"))
            throw new InvalidAuthorizationHeaderException();

        String[] split = authorizationHeader.split(" ");
        if (split.length != 2)
            throw new InvalidAuthorizationHeaderException();

        UserDetails userDetails = decodeToken(split[1]);
        return new RoleBasedAuthentication(userDetails.getUsername(), userDetails.getAuthorities());
    }

    protected abstract UserDetails decodeToken(String token) throws AuthenticationException;

}
