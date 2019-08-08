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
public class NotRegisteredAuthenticationProvidersException extends AuthenticationException {
    
    public NotRegisteredAuthenticationProvidersException() {
        super(Http.Status.INTERNAL_SERVER_ERROR, "There is no registered authentication providers");
    }
    
}
