/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.user;

import java.util.List;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authority;

/**
 *
 * @author joksin
 */
public interface UserDetails {
    
    public String getUsername();
    
    public String getPassword();
    
    public List<? extends Authority> getAuthorities();
    
}
