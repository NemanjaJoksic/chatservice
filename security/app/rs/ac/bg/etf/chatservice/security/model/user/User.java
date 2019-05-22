/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.List;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authority;

/**
 *
 * @author joksin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    private String username;
    private String password;
    private List<? extends Authority> authorities;
    
    public User() {
        
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorities = Collections.EMPTY_LIST;
    }

    public User(String username, String password, List<? extends Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<? extends Authority> getAuthorities() {
        return this.authorities;
    }
    
}
