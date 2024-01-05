package com.kittens.server.mapper;

import com.kittens.server.dto.RoomReadDto;
import com.kittens.server.entity.Room;
import com.kittens.server.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class RoomToRoomReadDto implements Mapper<Room, RoomReadDto>
{

    @Override
    public RoomReadDto map(Room object) {
        return new RoomReadDto(
                object.getId(),
                object.getUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toList()));
    }
}
