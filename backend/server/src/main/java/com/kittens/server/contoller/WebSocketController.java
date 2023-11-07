package com.kittens.server.contoller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {


    @MessageMapping("/hello")
    @SendTo("/topic/message")
    public String helloWorld(String message){
        //TODO: Логика
        return "Received message" + message;
    }
}
