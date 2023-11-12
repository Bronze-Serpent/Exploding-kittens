package com.kittens.card;

import com.kittens.action.Action;
import com.kittens.action.sudden.SuddenAction;

public interface Card
{

    CardName getName();

    Action getGettingAction();

    Action getPlayingAction();

    SuddenAction getSuddenPlayingAction();

}
