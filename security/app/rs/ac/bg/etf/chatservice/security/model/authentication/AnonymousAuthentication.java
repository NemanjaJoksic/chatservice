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
public class AnonymousAuthentication implements Authentication {

    @Override
    public List<? extends Authority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPrincipal() {
        return "AnonymousUser";
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
     
    }
    
}
