package com.kittens.server.entity;

import com.kittens.logic.card.CardName;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "card")
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
