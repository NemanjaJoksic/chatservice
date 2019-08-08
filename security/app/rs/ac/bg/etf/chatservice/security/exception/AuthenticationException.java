/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.exception;

import rs.ac.bg.etf.chatservice.shared.exception.GeneralException;

/**
 *
 * @author joksin
 */
public class AuthenticationException extends GeneralException {

    public AuthenticationException(Throwable t) {
        super(t);
    }
    
    public AuthenticationException(int httpStatus, String httpMessage) {
        super(httpStatus, httpMessage);
    }
    
}
