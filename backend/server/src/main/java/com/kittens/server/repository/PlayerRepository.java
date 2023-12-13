package com.kittens.server.repository;

import com.kittens.server.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;


public interface PlayerRepository extends CrudRepository<PlayerEntity, Long>
{

}
