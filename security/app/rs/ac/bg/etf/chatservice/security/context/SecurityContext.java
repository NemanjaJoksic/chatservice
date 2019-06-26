/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.context;

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
    private static int NEXT_ID = 1;
    
    public static SecurityContext current() {
        SecurityContext securityContext = securityContextHolder.get();
        if(securityContext == null) {
            throw new RuntimeException("There is no Security Context available from here.");
        }
        return securityContext;
    }
    
    public static void setAuthentication(Authentication authentication) {
        System.out.println("**** Thread: " + Thread.currentThread().getName());
        int id = NEXT_ID++;
        securityContextHolder.set(new SecurityContext(id, authentication));
    }
    
    private int id;
    private Authentication authentication;
            
}
