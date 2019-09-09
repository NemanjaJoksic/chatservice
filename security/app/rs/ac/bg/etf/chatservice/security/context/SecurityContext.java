/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.context;

import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;

/**
 *
 * @author joksin
 */
@Getter
@AllArgsConstructor
public class SecurityContext {
    
    private static final ThreadLocal<SecurityContext> securityContextHolder = new ThreadLocal<>();
    private static AtomicLong NEXT_ID = new AtomicLong();
    
    public static SecurityContext current() {
        SecurityContext securityContext = securityContextHolder.get();
        if(securityContext == null) {
            throw new RuntimeException("There is no Security Context available from here.");
        }
        return securityContext;
    }
    
    public static void setAuthentication(Authentication authentication) {
        long id = NEXT_ID.incrementAndGet();
        securityContextHolder.set(new SecurityContext(id, authentication));
    }
    
    private long id;
    private Authentication authentication;
            
}
