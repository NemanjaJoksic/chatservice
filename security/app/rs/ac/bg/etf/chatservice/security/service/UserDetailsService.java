/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.service;

import java.util.Optional;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;

/**
 *
 * @author joksin
 */
public interface UserDetailsService {
    
    public Optional<UserDetails> getUserByUsername(String username);
    
}
