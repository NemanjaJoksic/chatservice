/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author joksin
 */
@Configuration
@PropertySource("classpath:application.conf")
@Getter
public class Config {

    @Value("${akka.remote.netty.tcp.hostname}")
    private String actorTcpHostname;

    @Value("${akka.remote.netty.tcp.port}")
    private String actorTcpPort;
    
    @Value("${app.token.authentication.storage.jwt.algorithm}")
    private String jwtSignitureAlgorithm;
    
    @Value("${app.token.authentication.storage.jwt.signing-key}")
    private String jwtSigningKey;
    
    @Value("${app.akka.actor.storage.redis.host}")
    private String redisHost;
    
    @Value("${app.akka.actor.storage.redis.port}")
    private Integer redisPort;
    
}
