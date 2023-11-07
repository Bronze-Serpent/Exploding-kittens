package com.kittens.card;

import com.kittens.action.Action;
import com.kittens.action.sudden.SuddenCardAction;

public interface Card
{

    String getName();

    Action getGettingAction();

    Action getPlayingAction();

    SuddenCardAction getSuddenPlayingAction();

}
