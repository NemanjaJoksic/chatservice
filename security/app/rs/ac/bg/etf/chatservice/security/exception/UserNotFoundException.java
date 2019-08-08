/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.exception;

import play.mvc.Http;

/**
 *
 * @author joksin
 */
public class UserNotFoundException extends AuthenticationException {
    
    public UserNotFoundException() {
        super(Http.Status.UNAUTHORIZED, "User not found");
    }
    
}
