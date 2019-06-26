/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.controller;

import java.util.concurrent.CompletionStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;
import rs.ac.bg.etf.chatservice.chatmanager.service.ChatService;
import rs.ac.bg.etf.chatservice.security.context.SecurityContext;

/**
 *
 * @author joksin
 */
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    public CompletionStage<Result> connect(String dataType, String messageType) {
        System.out.println("#### Thread: " + Thread.currentThread().getName());
        String principal = SecurityContext.current().getAuthentication().getPrincipal();
        return chatService
                .connect(principal, dataType, messageType)
                .thenApplyAsync(connect -> {
                    return Results.ok(Json.toJson(connect));
                });
    }

}
