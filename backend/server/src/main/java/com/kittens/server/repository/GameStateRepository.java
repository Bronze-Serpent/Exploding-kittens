package com.kittens.server.repository;

import com.kittens.server.entity.GameStateEntity;
import org.springframework.data.repository.CrudRepository;


public interface GameStateRepository extends CrudRepository<GameStateEntity, Long>
{
}
