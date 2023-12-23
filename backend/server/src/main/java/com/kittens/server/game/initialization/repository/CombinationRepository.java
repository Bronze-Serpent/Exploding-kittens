package com.kittens.server.game.initialization.repository;

import com.kittens.server.game.initialization.entity.CombinationEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface CombinationRepository extends Repository<CombinationEntity, Integer>
{
    List<CombinationEntity> findAll();
}
