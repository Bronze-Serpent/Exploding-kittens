package com.kittens.logic.model;

import java.util.*;


public class LoopingListImpl<T> implements LoopingList<T>
{
    private final Map<T, T> tQueue = new HashMap<>();

    private T current;


    public LoopingListImpl(List<T> sourceList)
    {
        if (sourceList.size() == 0)
            throw new RuntimeException("sourceList должен иметь размер больше 0");

        T t = sourceList.get(0);
        current = t;

        if (sourceList.size() == 1)
        {
            tQueue.put(t, t);
            return;
        }

        for (int i = 0; i + 1 < sourceList.size(); )
        {
            tQueue.put(sourceList.get(i), sourceList.get(++i));
        }
        tQueue.put(sourceList.get(sourceList.size() - 1), sourceList.get(0));
    }

    @Override
    public void remove(T elem)
    {
        // 1 -> 2 -> 3 чтобы после удаления 2, если current = 2 при вызове next возвращался 3
        T pointingPlayer = getKeyForVal(elem);
        if (elem.equals(current))
        {
            current = pointingPlayer;
        }

        if (tQueue.containsKey(elem))
        {
            tQueue.put(pointingPlayer, tQueue.remove(elem));
        }
    }

    @Override
    public T next()
    {
        T next = tQueue.get(current);
        current = next;

        return next;
    }

    @Override
    public T getCurrent()
    {
        return current;
    }

    @Override
    public int size()
    {
        return tQueue.size();
    }

    @Override
    public List<T> getSequence()
    {
        List<T> returnList = new ArrayList<>();

        for (Map.Entry<T, T> entry : tQueue.entrySet())
        {
            returnList.add(entry.getKey());
        }

        return returnList;
    }

    @Override
    public void assignAWalker(T pLayer)
    {
        if (!tQueue.containsKey(pLayer))
            throw new RuntimeException("Ходящий должен быть в списке игроков (sourceList)");

        current = pLayer;
    }


    private T getKeyForVal(T val)
    {
        for (Map.Entry<T, T> entry : tQueue.entrySet())
        {
            if (entry.getValue().equals(val))
                return entry.getKey();
        }
        return null;
    }
}
