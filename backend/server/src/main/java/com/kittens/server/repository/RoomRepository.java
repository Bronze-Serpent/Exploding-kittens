package com.kittens.server.repository;

import com.kittens.server.entity.Room;
import com.kittens.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RoomRepository extends JpaRepository<Room, Long>
{
    @Query("select u " +
            "from Room r " +
            "inner join r.users u " +
            "where r.id = :roomId")
    List<User> findAllUserInRoom(@Param("roomId") Long roomId);


    @Query("select r " +
            "from Room r " +
            "inner join r.users u " +
            "where u.id = :userId")
    Optional<Room> findRoomFor(@Param("userId") Long userId);
}