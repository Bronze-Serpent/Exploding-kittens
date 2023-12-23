package com.kittens.logic.card;

import com.kittens.logic.action.Action;
import com.kittens.logic.action.sudden.SuddenAction;

public interface Card
{

    CardName getName();

    Action getGettingAction();

    Action getPlayingAction();

    SuddenAction getSuddenPlayingAction();

}
