/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.authentication;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author joksin
 */
public class RoleBasedAuthentication implements Authentication {

    private List<? extends Authority> roles; 
    private String principal;
    private boolean isAuthenticated = false;

    public RoleBasedAuthentication(String principal) {
        this.principal = principal;
        this.roles = Collections.EMPTY_LIST;
    }
    
    public RoleBasedAuthentication(String principal, List<? extends Authority> roles) {
        this.principal = principal;
        this.roles = roles;
    }
    
    @Override
    public List<? extends Authority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    
}
