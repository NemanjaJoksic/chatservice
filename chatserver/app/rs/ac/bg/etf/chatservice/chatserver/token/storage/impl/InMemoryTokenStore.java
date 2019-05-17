/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.token.storage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.IN_MEMORY_TICKET_STORAGE;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.chatserver.token.storage.TokenStore;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnProperty(name = "app.token.authentication.storage.type", havingValue = IN_MEMORY_TICKET_STORAGE)
public class InMemoryTokenStore implements TokenStore {
    
    private Map<String, TokenDetails> map;
    
    @PostConstruct
    public void initMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("tokens.json");
        List<TokenDetails> tokens = mapper.readValue(is,  new TypeReference<List<TokenDetails>>(){});
        map = tokens.stream()
                .collect(Collectors.toMap(TokenDetails::getToken, token -> token));
    }
    
    @Override
    public void put(String ticket, TokenDetails details) {
        map.put(ticket, details);
    }
    
    @Override
    public TokenDetails get(String ticket) {
        return map.get(ticket);
    }

    @Override
    public void remove(String ticket) {
        map.remove(ticket);
    }
    
}
