package com.kittens.server.entity;

import com.kittens.logic.combination.CombinationPredicate;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "combination")
@Getter
public class CombinationEntity extends BaseEntity<Integer>
{
    private String name;

    @ManyToOne
    private ActionEntity action;

    @Enumerated(value = EnumType.STRING)
    private CombinationPredicate predicate;

    private Boolean isEnabled;
}
