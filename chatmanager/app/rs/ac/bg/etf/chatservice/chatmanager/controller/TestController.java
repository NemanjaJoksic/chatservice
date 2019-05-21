/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.springframework.stereotype.Controller;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

/**
 *
 * @author joksin
 */
@Controller
public class TestController {
    
    public CompletionStage<Result> user() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", UUID.randomUUID().toString());
        map.put("username", "nemanja");
        return CompletableFuture.supplyAsync(() -> Results.ok(Json.toJson(map)));
    }
    
}
