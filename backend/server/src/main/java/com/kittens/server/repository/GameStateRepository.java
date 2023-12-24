package com.kittens.server.repository;

import com.kittens.server.entity.GameStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GameStateRepository extends JpaRepository<GameStateEntity, Long>
{
    Optional<GameStateEntity> findGameStateByRoomId(Long roomId);
}
