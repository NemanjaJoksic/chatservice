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
public class Chat {
    
    private String chatId;
    private String channelId;
    private String channelName;
    private String channelType;
    
}
