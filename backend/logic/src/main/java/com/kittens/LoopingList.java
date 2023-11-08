package com.kittens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LoopingList<T>
{
    private final List<T> sourceList;

    private Iterator<T> listIterator;

    private final List<T> elementsToRemove;


    public LoopingList(List<T> sourceList)
    {
        if (sourceList.size() == 0)
            throw new RuntimeException("sourceList должен иметь размер больше 0");

        this.sourceList = sourceList;
        listIterator = sourceList.iterator();
        this.elementsToRemove = new ArrayList<>();
    }

    public void remove(T elem)
    {
        if (sourceList.contains(elem))
            elementsToRemove.add(elem);
    }

    public T next()
    {
        if (!listIterator.hasNext())
        {
            for (T elemToRemove : elementsToRemove)
                sourceList.remove(elemToRemove);

            listIterator = sourceList.iterator();
        }
        return listIterator.next();
    }

    public int size()
    {
        return sourceList.size() - elementsToRemove.size();
    }

    public List<T> getSourceList()
    {
        List<T> returnList = new ArrayList<>(sourceList);

        for (T elemToRemove : elementsToRemove)
            returnList.remove(elemToRemove);

        return returnList;
    }
}
