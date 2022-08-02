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
public class InvalidDataTypeException extends ChatServerException {
    
    public InvalidDataTypeException(String dataType) {
        super(Http.Status.BAD_REQUEST, "Invalid data type " + dataType);
    }
    
}
