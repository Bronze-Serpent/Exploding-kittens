package com.kittens.server.entity.game.init;

import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@Entity
@Table(name = "sudden_action")
@EqualsAndHashCode(callSuper = false)
public class SuddenActionEntity extends BaseEntity<Integer>
{
    private String name;
}
