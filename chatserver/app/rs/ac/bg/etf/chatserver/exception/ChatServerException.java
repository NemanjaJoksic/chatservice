/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.exception;

import java.util.List;
import java.util.concurrent.CompletionException;
import play.mvc.Http;

/**
 *
 * @author joksin
 */
public class ChatServerException extends CompletionException  {
    
    private final int httpStatus;
    private final String httpMessage;
    
    private ChatServerException(Throwable t) {
        super(t);
        this.httpStatus = Http.Status.INTERNAL_SERVER_ERROR;
        this.httpMessage = t.getMessage();
    }
    
    private ChatServerException(int httpStatus, String httpMessage) {
        super(httpMessage);
        this.httpStatus = httpStatus;
        this.httpMessage = httpMessage;
    }
    
    public static ChatServerException generateException(ExceptionData ex) {
        ChatServerException exception = new ChatServerException(ex.getStatus(), ex.getMessage());
        return exception;
    }
    
    public static ChatServerException generateException(ExceptionData ex, List<String> args) {
        ChatServerException exception = new ChatServerException(ex.getStatus(), 
                replaceArgs(ex.getMessage(), args));
        return exception;
    }
    
    public static ChatServerException generateException(Throwable t) {
        ChatServerException exception = new ChatServerException(t);
        return exception;
    }
    
    private static String replaceArgs(String template, List<String> args) {
        String httpMessage = template;
        for(String arg : args)
            httpMessage = httpMessage.replaceFirst("#ARG#", arg);
        return httpMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getHttpMessage() {
        return httpMessage;
    }
    
}
