/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.Setter;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authority;

/**
 *
 * @author joksin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class User implements UserDetails {

    private String username;
    private String password;
    private List<Authority> authorities = new LinkedList<>();
    
    public User() {
        
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorities = Collections.EMPTY_LIST;
    }

    public User(String username, String password, List<Authority> authorities) {
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
    public List<Authority> getAuthorities() {
        return this.authorities;
    }
    
}
