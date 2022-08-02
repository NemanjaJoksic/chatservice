/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.crypto;

/**
 *
 * @author joksin
 */
public interface PasswordEncoder {
    
    public String encode(CharSequence rawPassword);

    public boolean matches(CharSequence rawPassword, String encodedPassword);
    
}
