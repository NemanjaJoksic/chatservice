/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author joksin
 */
@Getter
@AllArgsConstructor
public class Register {
    
    public static final String SUCCESS = "SUCCESS";
    
    private String username;
    private String status;
    
}
