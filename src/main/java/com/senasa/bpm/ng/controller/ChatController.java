package com.senasa.bpm.ng.controller;

import com.senasa.bpm.ng.model.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/medico") // Cambia "/medico" por el prefijo que desees para tus endpoints
@AllArgsConstructor
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/{chatId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;  // Propagar el mensaje solo a los suscritos al tópico de esa sesión de chat
    }
}



