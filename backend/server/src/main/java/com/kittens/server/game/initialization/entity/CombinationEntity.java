package com.kittens.server.game.initialization.entity;

import com.kittens.logic.combination.CombinationPredicate;
import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Entity
@Table(name = "combination")
@Getter
@EqualsAndHashCode(of = "name", callSuper = false)
public class CombinationEntity extends BaseEntity<Integer>
{
    private String name;

    @ManyToOne
    private ActionEntity action;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "predicate")
    private CombinationPredicate combPredicate;

    private Boolean isEnabled;
}
