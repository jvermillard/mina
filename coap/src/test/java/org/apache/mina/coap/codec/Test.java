package org.apache.mina.coap.codec;

import java.nio.ByteBuffer;

import org.apache.mina.api.AbstractIoHandler;
import org.apache.mina.api.IdleStatus;
import org.apache.mina.api.IoSession;
import org.apache.mina.coap.CoapCode;
import org.apache.mina.coap.CoapMessage;
import org.apache.mina.coap.CoapOption;
import org.apache.mina.coap.CoapOptionType;
import org.apache.mina.coap.MessageType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.nio.udp.NioUdpServer;

public class Test {

    public static void main(String[] args) {
        NioUdpServer server = new NioUdpServer();
        server.getSessionConfig().setIdleTimeInMillis(IdleStatus.READ_IDLE, 20000);
        server.setFilters(new ProtocolCodecFilter<CoapMessage, ByteBuffer, Void, Void>(new CoapEncoder(),
                new CoapDecoder()));
        server.setIoHandler(new AbstractIoHandler() {

            @Override
            public void sessionOpened(IoSession session) {
                System.err.println("open " + session);

            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus status) {
                System.err.println("idle " + session);
                session.close(false);
            }

            @Override
            public void sessionClosed(IoSession session) {
                System.err.println("closed");
            }

            @Override
            public void messageSent(IoSession session, Object message) {
                System.err.println("sent " + message + " to " + session);

            }

            @Override
            public void messageReceived(IoSession session, Object message) {
                System.err.println("receive " + message + " from " + session);
                CoapMessage msg = (CoapMessage) message;
                if (msg.getType() == MessageType.CONFIRMABLE && CoapCode.fromCode(msg.getCode()) == CoapCode.GET) {
                    // it's a get!

                    // find the URI
                    String url = null;
                    for (CoapOption opt : msg.getOptions()) {
                        if (opt.getType() == CoapOptionType.URI_PATH) {
                            url = new String(opt.getData());
                        }
                    }

                    System.err.println("GET on path : " + url);

                    // let's confirm it
                    CoapMessage response = new CoapMessage(1, MessageType.ACK, CoapCode.CONTENT.getCode(), msg.getId(),
                            msg.getToken(), "hello coap".getBytes(), new CoapOption[] { new CoapOption(
                                    CoapOptionType.CONTENT_FORMAT, new byte[] { 0 }) });
                    session.write(response);
                }
            }

            @Override
            public void exceptionCaught(IoSession session, Throwable cause) {
                System.err.println("exception : ");
                cause.printStackTrace();
                session.close(false);
            }
        });

        try {
            server.bind(5683);

            Thread.sleep(60000);

            server.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
