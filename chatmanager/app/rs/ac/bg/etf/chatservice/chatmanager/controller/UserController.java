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
import rs.ac.bg.etf.chatservice.chatmanager.service.UserService;

/**
 *
 * @author joksin
 */
@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    public CompletionStage<Result> register() {
        return userService.register()
                .thenApplyAsync(connect -> {
                    return Results.ok(Json.toJson(connect));
                });
    }
    
}
