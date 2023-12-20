package com.kittens.logic.card;

import com.kittens.logic.action.Action;
import com.kittens.logic.action.sudden.SuddenAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@ToString(of = "name")
@RequiredArgsConstructor
public class OrdinaryCard implements Card
{

    private final CardName name;

    private final Action gettingAction;
    private final Action playingAction;
    private final SuddenAction suddenPlayingAction;

}


