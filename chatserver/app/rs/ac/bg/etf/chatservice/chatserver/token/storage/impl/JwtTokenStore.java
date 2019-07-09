/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.token.storage.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.chatserver.Config;
import rs.ac.bg.etf.chatservice.chatserver.token.storage.TokenStore;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.JWT_TOKEN_STORAGE;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.TOKEN_STORAGE_TYPE;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnProperty(name = TOKEN_STORAGE_TYPE, havingValue = JWT_TOKEN_STORAGE)
public class JwtTokenStore implements TokenStore {

    @Autowired
    private Config config;

    @Override
    public void put(String ticket, TokenDetails details) {

    }

    @Override
    public TokenDetails get(String token) throws ChatServiceException {
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(config.getJwtSigningKey()).parseClaimsJws(token);
            Claims claims = jwsClaims.getBody();
            String userId = claims.getSubject();
            String channel = claims.get("channel", String.class);
            long timestamp = claims.getExpiration().getTime();
            return new TokenDetails(token, userId, channel, timestamp);
        } catch (ExpiredJwtException ex) { 
            throw ChatServiceException.generateException(ExceptionData.TOKEN_EXPIRED, token);
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ExceptionData.INVALID_TOKEN, token);
        }
    }

    @Override
    public void remove(String ticket) {

    }

}
