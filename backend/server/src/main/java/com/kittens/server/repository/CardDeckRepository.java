package com.kittens.server.repository;

import com.kittens.server.entity.CardDeck;
import org.springframework.data.repository.CrudRepository;


public interface CardDeckRepository extends CrudRepository<CardDeck, Long>
{

}
