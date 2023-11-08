package com.kittens.card;

import com.kittens.action.Action;
import com.kittens.action.sudden.SuddenAction;

public interface Card
{

    String getName();

    Action getGettingAction();

    Action getPlayingAction();

    SuddenAction getSuddenPlayingAction();

}
