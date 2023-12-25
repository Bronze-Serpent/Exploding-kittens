package com.kittens.server.contoller;

import com.kittens.server.dto.EndYouTurnDto;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.dto.PlayCombinationDto;
import com.kittens.server.service.GameService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameController
{
    GameService gameService;


    @PostMapping("/{roomId}/end-your-turn")
    public ResponseEntity<?> endYourTurn(
            @PathVariable Long roomId,
            @RequestBody EndYouTurnDto dto
    )
    {
        gameService.endYourTurn(roomId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roomId}/play-combination")
    public ResponseEntity<?> playCombination(
            @PathVariable Long roomId,
            @RequestBody PlayCombinationDto dto
    )
    {
        gameService.playCombination(roomId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roomId}/play-card")
    public ResponseEntity<?> playCard(
            @PathVariable Long roomId,
            @RequestBody PlayCardDto dto
    )
    {
        gameService.playCard(roomId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roomId}/init")
    public ResponseEntity<?> initGameStateInRoom(@PathVariable Long roomId)
    {
        gameService.initGameInRoom(roomId);
        return ResponseEntity.ok().build();
    }

}
