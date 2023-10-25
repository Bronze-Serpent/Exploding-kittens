package com.kittens.card;

public interface Card
{

    String getName();

    default Action getGettingAction()
    {
        return Action.INACTION;
    }

    default Action getPlayingAction()
    {
        return Action.INACTION;
    }

    default SuddenAction getSuddenPlayingAction()
    {
        return SuddenAction.INACTION;
    }


}
