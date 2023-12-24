package com.kittens.server.service.impl;

import com.kittens.server.entity.Room;
import com.kittens.server.repository.RoomRepository;
import com.kittens.server.service.RoomService;
import com.kittens.server.user.entity.User;
import com.kittens.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class RoomServiceImpl implements RoomService
{
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    @Override
    public Long createEmptyRoom()
    {
        return roomRepository.save(new Room())
                .getId();
    }

    @Override
    public void addUserToRoom(Long roomId, Long userId)
    {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Невозможно добавить user с id: "
                        + userId + " в комнату с id: " + roomId + " т.к. комната с таким id не найдена"));

        room.addUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Невозможно добавить user с id: "
                + userId + " в комнату с id: " + roomId + " т.к. user с таким id не найден")));

        roomRepository.saveAndFlush(room);
    }

    @Override
    public List<Long> getAllUsersId(Long roomId)
    {
        return roomRepository.findAllUserInRoom(roomId).stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
