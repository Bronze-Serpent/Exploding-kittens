package com.kittens.server.contoller;

import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.service.GameService;
import com.kittens.server.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameController {

    GameService gameService;

    @PostMapping("/{roomId}/play-card")
    public ResponseEntity<?> playCard(
            @PathVariable Long roomId,
            @RequestBody PlayCardDto dto
    ) {
        gameService.playCard(roomId, dto);
        return ResponseEntity.ok().build();
    }
}
