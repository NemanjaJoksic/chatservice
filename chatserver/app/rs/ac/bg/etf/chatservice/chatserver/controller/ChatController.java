/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.mvc.*;
import rs.ac.bg.etf.chatservice.chatserver.service.ChatService;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.TEXT_DATA;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.BINARY_DATA;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    public WebSocket chat(String dataType, String messageType, String token) throws ChatServiceException {

        switch (dataType) {
            case TEXT_DATA:
                return WebSocket.Text.acceptOrResult(request -> {
                    return chatService.openConnection(dataType, messageType, token);
                });
            case BINARY_DATA:
                return WebSocket.Binary.acceptOrResult(request -> {
                    return chatService.openConnection(dataType, messageType, token);
                });
            default:
                throw ChatServiceException.generateException(ExceptionData.INVALID_DATA_TYPE, 
                        Arrays.asList(dataType));
        }
    }

}
