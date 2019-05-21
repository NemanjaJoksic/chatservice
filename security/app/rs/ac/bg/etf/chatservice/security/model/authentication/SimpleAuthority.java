/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.authentication;

import lombok.AllArgsConstructor;

/**
 *
 * @author joksin
 */
@AllArgsConstructor
public class SimpleAuthority implements Authority {

    private String authority;
    
    @Override
    public String getAuthority() {
        return this.authority;
    }
    
}
