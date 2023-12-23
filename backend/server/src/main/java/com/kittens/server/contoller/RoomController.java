package com.kittens.server.contoller;

import com.kittens.server.dto.AddUserToRoomDto;
import com.kittens.server.service.GameService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/room")
@RestController
public class RoomController
{
    GameService gameService;


    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody AddUserToRoomDto addUserToRoomDto)
    {
        gameService.createRoom(addUserToRoomDto.getUserId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{roomId}/join")
    public ResponseEntity<?> addUserToRoom(@PathVariable Long roomId,
                                           @RequestBody AddUserToRoomDto addUserToRoomDto)
    {
        gameService.addUserToRoom(roomId, addUserToRoomDto.getUserId());
        return ResponseEntity.ok().build();
    }
}
