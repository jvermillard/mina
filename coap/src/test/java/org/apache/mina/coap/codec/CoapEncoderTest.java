package org.apache.mina.coap.codec;

import java.nio.ByteBuffer;

import org.apache.mina.coap.CoapMessage;
import org.apache.mina.coap.CoapOption;
import org.apache.mina.coap.CoapOptionType;
import org.apache.mina.coap.MessageType;
import org.apache.mina.util.ByteBufferDumper;
import org.junit.Test;

public class CoapEncoderTest {

    @Test
    public void total_encode() {
        CoapEncoder encoder = new CoapEncoder();

        CoapMessage message = new CoapMessage(1, MessageType.NON_CONFIRMABLE, 1, 1234, "toto".getBytes(),
                new byte[] {}, new CoapOption[] { new CoapOption(CoapOptionType.URI_PATH, "coap://blabla".getBytes()) });
        System.err.println(message);
        ByteBuffer encoded = encoder.encode(message, null);
        System.err.println(ByteBufferDumper.dump(encoded));

        CoapDecoder decoder = new CoapDecoder();
        System.err.println(decoder.decode(encoded, null));
    }
}
