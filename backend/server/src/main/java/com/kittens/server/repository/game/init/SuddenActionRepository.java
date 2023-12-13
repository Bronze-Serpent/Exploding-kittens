package com.kittens.server.repository.game.init;

import com.kittens.server.entity.game.init.SuddenActionEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface SuddenActionRepository extends Repository<SuddenActionEntity, Integer>
{
    List<SuddenActionEntity> findAll();
}
