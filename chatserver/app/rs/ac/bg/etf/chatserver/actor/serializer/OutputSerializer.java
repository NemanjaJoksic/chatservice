/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.serializer;

/**
 *
 * @author joksin
 */
public interface OutputSerializer {
    
    public Object serialize(byte[] bytes);
    
}
