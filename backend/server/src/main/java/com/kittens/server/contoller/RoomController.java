package com.kittens.server.contoller;

import com.kittens.server.dto.AddUserToRoomDto;
import com.kittens.server.dto.RoomDescriptionDto;
import com.kittens.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/room")
@RestController
public class RoomController
{
    private final RoomService roomService;


    @PostMapping("/create")
    public RoomDescriptionDto createRoom(@RequestBody AddUserToRoomDto addUserToRoomDto)
    {
        Long createdRoomId = roomService.createRoom(addUserToRoomDto.getUserId());

        return new RoomDescriptionDto(
                createdRoomId,
                roomService.getAllUsersId(createdRoomId)
        );
    }


    // TODO: 24.12.2023  тут мы должны добавляющемуся пользователю по вебсокету отправлять или же из запроса?
    @PostMapping("/{roomId}/join")
    public RoomDescriptionDto enterTheRoom(@PathVariable Long roomId,
                                          @RequestBody AddUserToRoomDto addUserToRoomDto)
    {
        roomService.addUserToRoom(roomId, addUserToRoomDto.getUserId());

        // TODO: 24.12.2023 если в roomService.addUserToRoom сделать человечески ошибки
        //  то тут в зависимости от того выпадает ли ошибка или нет при добавлении формировать ответ пользователю

        return new RoomDescriptionDto(
                roomId,
                roomService.getAllUsersId(roomId)
        );
    }
}
