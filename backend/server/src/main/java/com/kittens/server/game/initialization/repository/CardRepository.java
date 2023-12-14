package com.kittens.server.game.initialization.repository;

import com.kittens.server.game.initialization.entity.CardEntity;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface CardRepository extends Repository<CardEntity, Integer>
{
    List<CardEntity> findAll();
}
