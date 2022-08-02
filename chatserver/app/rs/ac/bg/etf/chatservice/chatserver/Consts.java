/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver;

/**
 *
 * @author joksin
 */
public class Consts {
    
    public static final String TEXT_DATA = "text";
    public static final String BINARY_DATA = "binary";
    
    public static final String JSON_SERIALIZATION = "json";
    public static final String PROTOBUF_SERIALIZATION = "protobuf";
    
    public static final String ACTOR_STORAGE_TYPE = "app.akka.actor.storage.type";
    public static final String IN_MEMORY_ACTOR_STORAGE = "in-memory";
    public static final String REDIS_ACTOR_STORAGE = "redis";
    
    public static final String STORAGE_TICKET_AUTHENTICATOR = "storage";
    public static final String TOKEN_STORAGE_TYPE = "app.token.authentication.storage.type";
    public static final String IN_MEMORY_TOKEN_STORAGE = "in-memory";
    public static final String JWT_TOKEN_STORAGE = "jwt";
    
    public static final String HAS_MAP_CACHE = "hash-map";
    public static final String NO_CACHE = "no-cache";
    
    public static final String TOPIC_ACTOR = "actor";
    
    public static final String STORAGE_DISPATCHER = "akka.actor.storage-dispatcher";
    public static final String WEBSOCKET_DISPATCHER = "akka.actor.websocket-dispatcher";
    public static final String ACTOR_DISPATCHER = "akka.actor.actor-dispatcher";
    
    
}
