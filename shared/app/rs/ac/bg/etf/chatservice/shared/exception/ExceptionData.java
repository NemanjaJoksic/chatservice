/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shared.exception;

import play.mvc.Http;

/**
 *
 * @author joksin
 */
public enum ExceptionData {

    // chat server exceptions
    TOKEN_EXPIRED("Token [#ARG#] has expired", Http.Status.UNAUTHORIZED),
    INVALID_TOKEN("Invalid token [#ARG#]", Http.Status.UNAUTHORIZED),
    INVALID_MESSAGE_TYPE("Invalid message type [#ARG#]", Http.Status.BAD_REQUEST),
    INVALID_DATA_TYPE("Invalid data type [#ARG#]", Http.Status.BAD_REQUEST),
    UNABLE_TO_CONNECT_TO_REDIS("Unable to connect to redis server #ARG#:#ARG#", Http.Status.INTERNAL_SERVER_ERROR),
    REDIS_CONNECTION_ERROR("Redis connection error", Http.Status.INTERNAL_SERVER_ERROR),
    
    // securiy exceptions
    NOT_REGISTERED_AUTHENTICATED_PROVIDERS("There is no registered authentication providers", Http.Status.INTERNAL_SERVER_ERROR),
    MISSING_AUTHORIZATION_HEADER("Missing authorization header", Http.Status.BAD_REQUEST),
    USER_NOT_FOUNT("User not found", Http.Status.UNAUTHORIZED),
    WRONG_PASSWORD("Wrong password", Http.Status.BAD_REQUEST),
    INVALID_AUTHORIZATION_HEADER("Authorization header is invalid", Http.Status.BAD_REQUEST)
    
    ;
    

    private final int status;
    private final String message;

    private ExceptionData(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    
}
