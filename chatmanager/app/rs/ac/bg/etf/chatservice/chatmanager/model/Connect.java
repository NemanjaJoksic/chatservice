/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author joksin
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Connect {
    
    private String userId;
    private String dataType;
    private String messageType;
    private int expiresIn;
    private String chatServerUrl;
    
    public Connect(String chatServerUrl) {
        this.chatServerUrl = chatServerUrl;
    }
    
}
