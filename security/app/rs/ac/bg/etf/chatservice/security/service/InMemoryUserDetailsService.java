/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;

/**
 *
 * @author joksin
 */
@Repository
public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> userDetailsMap = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init() {
        UserDetails userDetails1 = new User("nemanja", "123");
        userDetailsMap.put("nemanja", userDetails1);
    }
    
    @Override
    public Optional<UserDetails> getUserByUsername(String username) {
        return Optional.ofNullable(userDetailsMap.get(username));
    }
    
}
