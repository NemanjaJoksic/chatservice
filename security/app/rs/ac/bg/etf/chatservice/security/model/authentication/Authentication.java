/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.authentication;

import java.util.List;

/**
 *
 * @author joksin
 */
public interface Authentication {

    public List<? extends Authority> getAuthorities();

    public String getPrincipal();

    public boolean isAuthenticated();

    public void setAuthenticated(boolean isAuthenticated);
    
}
