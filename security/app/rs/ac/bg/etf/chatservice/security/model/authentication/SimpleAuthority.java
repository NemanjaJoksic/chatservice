/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.authentication;

import java.util.Objects;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleAuthority other = (SimpleAuthority) obj;
        if (!Objects.equals(this.authority, other.authority)) {
            return false;
        }
        return true;
    }
    
}
