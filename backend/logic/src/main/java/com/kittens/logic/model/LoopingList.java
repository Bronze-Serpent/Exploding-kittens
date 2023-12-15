package com.kittens.logic.model;

import java.util.List;


public interface LoopingList<T>
{
    int size();

    List<T> getSequence();

    void assignAWalker(T pLayer);

    T getCurrent();

    T next();

    void remove(T elem);
}
