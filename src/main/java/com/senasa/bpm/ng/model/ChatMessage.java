package com.senasa.bpm.ng.model;

import lombok.*;

import java.time.LocalDateTime;
import lombok.Data;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Data
public class ChatMessage {
  private String senderId;  // Identificador del usuario que envía el mensaje
  private String recipientId;  // Identificador del usuario receptor (opcional para chat grupal)
  private String content;  // Contenido del mensaje
  private String chatId;  // Identificador de la conversación
}


