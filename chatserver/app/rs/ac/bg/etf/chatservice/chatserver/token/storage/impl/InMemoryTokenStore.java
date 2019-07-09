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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.chatserver.token.storage.TokenStore;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.IN_MEMORY_TOKEN_STORAGE;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.TOKEN_STORAGE_TYPE;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnProperty(name = TOKEN_STORAGE_TYPE, havingValue = IN_MEMORY_TOKEN_STORAGE)
public class InMemoryTokenStore implements TokenStore {

    private Map<String, TokenDetails> map;

    @PostConstruct
    public void initMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("tokens.json");
        List<TokenDetails> tokens = mapper.readValue(is, new TypeReference<List<TokenDetails>>() {
        });
        map = tokens.stream()
                .collect(Collectors.toMap(TokenDetails::getToken, token -> token));
    }

    @Override
    public void put(String ticket, TokenDetails details) {
    }

    @Override
    public TokenDetails get(String token) {
        return map.get(token);
    }

    @Override
    public void remove(String token) {
    }

}
