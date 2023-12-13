package com.kittens.server.repository.game.init;

import com.kittens.server.entity.game.init.ActionEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface ActionRepository extends Repository<ActionEntity, Integer>
{
    List<ActionEntity> findAll();
}
