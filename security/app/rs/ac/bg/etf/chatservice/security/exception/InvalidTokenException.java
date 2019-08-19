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
public class InvalidTokenException extends AuthenticationException {
    
    public InvalidTokenException(String message) {
        super(Http.Status.UNAUTHORIZED, message);
    }
    
}
