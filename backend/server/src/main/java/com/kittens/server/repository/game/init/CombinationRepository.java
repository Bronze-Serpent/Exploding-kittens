package com.kittens.server.repository.game.init;

import com.kittens.server.entity.game.init.CombinationEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface CombinationRepository extends Repository<CombinationEntity, Integer>
{
    List<CombinationEntity> findAll();
}
