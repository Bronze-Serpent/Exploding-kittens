package com.kittens.server.contoller;

import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.service.UserQuestioner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketController {
    UserQuestioner userQuestioner;

    @MessageMapping("/{roomId}/sudden-card")
    public void playSuddenCard(
            @DestinationVariable Long roomId,
            PlayCardDto message
    ) {
        userQuestioner.responseToPlaySuddenCards(roomId, message);
    }
}
