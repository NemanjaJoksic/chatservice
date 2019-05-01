/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.mvc.*;
import rs.ac.bg.etf.chatserver.service.ChatService;
import static rs.ac.bg.etf.chatserver.Consts.TEXT_DATA;
import static rs.ac.bg.etf.chatserver.Consts.BINARY_DATA;
import rs.ac.bg.etf.chatserver.exception.ChatServerException;
import rs.ac.bg.etf.chatserver.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    public WebSocket chat(String dataType, String messageType, String ticket) throws ChatServerException {

        switch (dataType) {
            case TEXT_DATA:
                return WebSocket.Text.acceptOrResult(request -> {
                    return chatService.openConnection(dataType, messageType, ticket);
                });
            case BINARY_DATA:
                return WebSocket.Binary.acceptOrResult(request -> {
                    return chatService.openConnection(dataType, messageType, ticket);
                });
            default:
                throw ChatServerException.generateException(ExceptionData.INVALID_DATA_TYPE, 
                        Arrays.asList(dataType));
        }
    }

}
