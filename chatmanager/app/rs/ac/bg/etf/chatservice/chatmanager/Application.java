/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author joksin
 */
@ComponentScan(basePackages = "rs.ac.bg.etf.chatservice")
@Configuration
@PropertySource("classpath:application.conf")
public class Application {
    
}
