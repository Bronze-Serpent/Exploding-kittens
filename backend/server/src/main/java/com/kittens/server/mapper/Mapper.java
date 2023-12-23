package com.kittens.server.mapper;

public interface Mapper <F, T>
{
    T map(F object);
}
