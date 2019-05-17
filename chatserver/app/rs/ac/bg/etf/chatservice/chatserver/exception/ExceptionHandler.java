/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Result;
import play.mvc.Results;

/**
 *
 * @author joksin
 */
public class ExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    
    public static Result handleException(Throwable t) {
        logger.error(t.getMessage(), t);
        if(t instanceof ChatServerException) {
            ChatServerException ex = (ChatServerException) t;
            return Results.status(ex.getHttpStatus(), ex.getHttpMessage());
        } else {
            return Results.internalServerError(t.getMessage());
        }
    }
    
}
