package com.kittens.server.repository;

import com.kittens.server.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>
{

}
