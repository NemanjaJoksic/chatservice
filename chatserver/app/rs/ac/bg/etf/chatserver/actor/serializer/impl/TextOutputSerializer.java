/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.serializer.impl;

import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.actor.serializer.OutputSerializer;
import static rs.ac.bg.etf.chatserver.Consts.TEXT_DATA;

/**
 *
 * @author joksin
 */
@Component(TEXT_DATA)
public class TextOutputSerializer implements OutputSerializer {

    @Override
    public Object serialize(byte[] bytes) {
        return new String(bytes);
    }
    
}
