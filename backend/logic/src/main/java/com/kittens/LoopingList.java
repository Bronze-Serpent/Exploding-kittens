package com.kittens;

import java.util.Iterator;
import java.util.List;


public class LoopingList<T>
{
    private final List<T> players;

    private Iterator<T> playingIterator;


    public LoopingList(List<T> players)
    {
        this.players = players;
        playingIterator = players.iterator();
    }


    public void remove(T player)
    {
        players.remove(player);
    }


    public T next()
    {
        if (!playingIterator.hasNext())
            playingIterator = players.iterator();

        return playingIterator.next();
    }

    public int size()
    {
        return players.size();
    }
}
