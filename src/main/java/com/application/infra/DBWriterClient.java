package com.application.infra;

import com.message.decoder.MessageDecoder;
import com.message.encoder.MessageEncoder;

import javax.websocket.ClientEndpoint;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class DBWriterClient {
}
