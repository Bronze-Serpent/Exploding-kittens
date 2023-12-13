package com.kittens.server.repository.game.init;

import com.kittens.server.entity.game.init.CardEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface CardRepository extends Repository<CardEntity, Integer>
{
    List<CardEntity> findAll();
}
