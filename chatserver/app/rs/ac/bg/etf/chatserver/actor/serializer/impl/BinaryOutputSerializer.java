/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.serializer.impl;

import akka.util.ByteString;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.actor.serializer.OutputSerializer;
import static rs.ac.bg.etf.chatserver.Consts.BINARY_DATA;

/**
 *
 * @author joksin
 */
@Component(BINARY_DATA)
public class BinaryOutputSerializer implements OutputSerializer {

    @Override
    public Object serialize(byte[] bytes) {
        return ByteString.fromArray(bytes);
    }
    
}
