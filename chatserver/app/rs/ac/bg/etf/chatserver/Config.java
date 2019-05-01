/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author joksin
 */
@Configuration
@PropertySource("classpath:application.conf")
public class Config {

    @Value("${akka.remote.netty.tcp.hostname}")
    private String actorTcpHostname;

    @Value("${akka.remote.netty.tcp.port}")
    private String actorTcpPort;

    public String getActorTcpHostname() {
        return actorTcpHostname;
    }

    public String getActorTcpPort() {
        return actorTcpPort;
    }
    
}
