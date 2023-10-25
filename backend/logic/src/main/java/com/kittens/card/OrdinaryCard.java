package com.kittens.card;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode(of = "name")
public class OrdinaryCard implements Card
{

    private final String name;

    private final Action gettingAction;
    private final Action playingAction;
    private final SuddenAction suddenPlayingAction;


    public OrdinaryCard(String name)
    {
        this.name = name;
        this.playingAction = Action.INACTION;
        this.gettingAction = Action.INACTION;
        this.suddenPlayingAction = SuddenAction.INACTION;
    }

    public OrdinaryCard(String name, Action playingAction)
    {
        this.name = name;
        this.playingAction = playingAction;
        this.gettingAction = Action.INACTION;
        this.suddenPlayingAction = SuddenAction.INACTION;
    }

    public OrdinaryCard(String name, Action playingAction, Action gettingAction, SuddenAction suddenPlayingAction)
    {
        this.name = name;
        this.playingAction = playingAction;
        this.gettingAction = gettingAction;
        this.suddenPlayingAction = suddenPlayingAction;
    }
}


