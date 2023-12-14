package com.kittens.server.game.initialization.entity;

import com.kittens.logic.card.CardName;
import com.kittens.server.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@Entity
@Table(name = "card")
@EqualsAndHashCode(of = "name", callSuper = false)
public class CardEntity extends BaseEntity<Integer>
{
    @Enumerated(EnumType.STRING)
    private CardName name;

    @ManyToOne
    private ActionEntity gettingAction;

    @ManyToOne
    private ActionEntity playingAction;

    @ManyToOne
    private SuddenActionEntity suddenAction;
}
