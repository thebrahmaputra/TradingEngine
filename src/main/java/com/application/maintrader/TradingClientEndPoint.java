package com.application.maintrader;

import com.message.Message;
import com.message.decoder.MessageDecoder;
import com.message.encoder.MessageEncoder;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.text.SimpleDateFormat;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class TradingClientEndPoint {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnMessage
    public void onMessage(Message message){
        System.out.println("[" + message.getClient() + " : " + message.getReceived() +
                        "] : " + message.getStock());
    }
}
