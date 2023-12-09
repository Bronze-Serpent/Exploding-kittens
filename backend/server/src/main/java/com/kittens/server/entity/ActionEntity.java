package com.kittens.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Table(name = "action")
public class ActionEntity extends BaseEntity<Integer>
{
    private String name;
}
