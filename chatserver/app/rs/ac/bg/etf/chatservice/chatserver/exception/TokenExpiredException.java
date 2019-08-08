/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.exception;

import play.mvc.Http;

/**
 *
 * @author joksin
 */
public class TokenExpiredException extends ChatServerException {
    
    public TokenExpiredException(String token) {
        super(Http.Status.FORBIDDEN, "Token has expired " + token);
    }
    
}
