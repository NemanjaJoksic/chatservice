/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.model;

import lombok.Getter;

/**
 *
 * @author joksin
 */
@Getter
public class Connect {
    
    private String chatServerUrl;
    
    public Connect(String chatServerUrl) {
        this.chatServerUrl = chatServerUrl;
    }
    
}
