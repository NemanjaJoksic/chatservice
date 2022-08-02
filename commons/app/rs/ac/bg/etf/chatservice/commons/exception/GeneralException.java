/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.commons.exception;

import java.util.concurrent.CompletionException;
import lombok.Getter;
import play.mvc.Http;

/**
 *
 * @author joksin
 */
@Getter
public class GeneralException extends CompletionException {
    
    private final int httpStatus;
    private final String httpMessage;
    
    public GeneralException(Throwable t) {
        super(t);
        this.httpStatus = Http.Status.INTERNAL_SERVER_ERROR;
        this.httpMessage = t.getMessage();
    }
    
    public GeneralException(int httpStatus, String httpMessage) {
        super(httpMessage);
        this.httpStatus = httpStatus;
        this.httpMessage = httpMessage;
    }
    
}
