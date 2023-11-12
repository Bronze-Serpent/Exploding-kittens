package com.kittens.server.component;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SuppressWarnings("FieldCanBeLocal")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebSocketInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //TODO: проверять токены
        System.out.println("preSend");
        System.out.println("Message: " + message);
        System.out.println("Channel: " + channel);
        return message;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        //TODO: проверять токены
        System.out.println("postReceive");
        System.out.println("Message: " + message);
        System.out.println("Channel: " + channel);
        return message;
    }
}
