package com.kittens.logic.model;


import java.util.Map;
import java.util.Set;

public interface LoopingList<T>
{
    int size();

    Set<T> getElements();

    Map<T, T> getPairs();

    void assignAWalker(T pLayer);

    T getCurrent();

    T next();

    void remove(T elem);
}
