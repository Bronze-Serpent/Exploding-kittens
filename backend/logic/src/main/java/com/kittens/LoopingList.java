package com.kittens;

import lombok.Getter;

import java.util.Iterator;
import java.util.List;


public class LoopingList<T>
{
    @Getter
    private final List<T> sourceList;

    private Iterator<T> listIterator;


    public LoopingList(List<T> sourceList)
    {
        this.sourceList = sourceList;
        listIterator = sourceList.iterator();
    }

    public void remove(T elem)
    {
        sourceList.remove(elem);
    }

    public T next()
    {
        if (!listIterator.hasNext())
            listIterator = sourceList.iterator();

        return listIterator.next();
    }

    public int size()
    {
        return sourceList.size();
    }
}
