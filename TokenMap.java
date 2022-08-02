/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shared.map;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author joksin
 */
public class TokenMap {

    LoadingCache<Integer, String> store;

    public TokenMap() {
        store = CacheBuilder.newBuilder()
                .maximumSize(4)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, String>() {
                    public String load(Integer key) throws Exception {
                        String uuid = UUID.randomUUID().toString();
                        System.out.println("Loading value: " + uuid + " for key: " + key);
                        return uuid;
                    }
                });
    }
    
    public String get(Integer key) throws ExecutionException {
        return store.get(key);
    }

    public static void main(String[] args) throws ExecutionException {
        TokenMap tokenMap = new TokenMap();
        for(int i = 0; i < 5; i++) {
            String token = tokenMap.get(i);
            System.out.println(token);
        }
        for(int i = 4; i >= 0; i--) {
            String token = tokenMap.get(i);
            System.out.println(token);
        }
    }
}
