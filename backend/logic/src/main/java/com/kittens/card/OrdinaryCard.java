package com.kittens.card;

import com.kittens.action.Action;
import com.kittens.action.sudden.SuddenAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
@RequiredArgsConstructor
public class OrdinaryCard implements Card
{

    private final String name;

    private final Action gettingAction;
    private final Action playingAction;
    private final SuddenAction suddenPlayingAction;

}


