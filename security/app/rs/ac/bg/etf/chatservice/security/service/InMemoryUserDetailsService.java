/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.security.crypto.PasswordEncoder;
import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;

/**
 *
 * @author joksin
 */
@Repository
public class InMemoryUserDetailsService implements UserDetailsService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, UserDetails> userDetailsMap;
    
    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("users.json");
        List<User> users = mapper.readValue(is,  new TypeReference<List<User>>(){});
        userDetailsMap = users.stream()
                .collect(Collectors.toMap(User::getUsername, user -> 
                        new User(user.getUsername(), passwordEncoder.encode(user.getPassword())))
                );
    }
    
    @Override
    public Optional<UserDetails> getUserByUsername(String username) {
        return Optional.ofNullable(userDetailsMap.get(username));
    }
    
}
