package com.kittens.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Table(name = "sudden_action")
public class SuddenActionEntity extends BaseEntity<Integer>
{
    private String name;
}
