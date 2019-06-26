/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import rs.ac.bg.etf.chatservice.chatmanager.service.UserService;
import rs.ac.bg.etf.chatservice.security.model.user.User;

/**
 *
 * @author joksin
 */
@Controller
public class UserController extends play.mvc.Controller {

    @Autowired
    private UserService userService;
    
    private final ObjectMapper mapper = new ObjectMapper();

    public CompletionStage<Result> register() throws IOException {
        return userService.register(mapper.treeToValue(request().body().asJson(), User.class))
                .thenApplyAsync(register -> {
                    return Results.ok(Json.toJson(register));
                });
    }

}
