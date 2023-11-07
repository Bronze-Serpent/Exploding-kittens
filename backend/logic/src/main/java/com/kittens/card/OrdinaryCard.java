package com.kittens.card;

import com.kittens.action.Action;
import com.kittens.action.sudden.SuddenCardAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@EqualsAndHashCode(of = "name")
@RequiredArgsConstructor
public class OrdinaryCard implements Card
{

    private final String name;

    private final Action gettingAction;
    private final Action playingAction;
    private final SuddenCardAction suddenPlayingAction;

}


