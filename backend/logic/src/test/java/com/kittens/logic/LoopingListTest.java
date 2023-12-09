package com.kittens.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LoopingListTest
{
    private final List<Integer> sourceList = new ArrayList<>(List.of(1, 2, 3));

    private final LoopingList<Integer> loopingList = new LoopingList<>(sourceList);

    @Test
    public void shouldSpinTheList()
    {
        assertThat(loopingList.getCurrent()).isEqualTo(1);
        assertThat(loopingList.next()).isEqualTo(2);
        assertThat(loopingList.next()).isEqualTo(3);
        assertThat(loopingList.next()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveElem()
    {
        assertThat(loopingList.getCurrent()).isEqualTo(1);
        assertThat(loopingList.next()).isEqualTo(2);
        loopingList.remove(2);
        assertThat(loopingList.next()).isEqualTo(3);

        assertThat(loopingList.next()).isEqualTo(1);
        assertThat(loopingList.next()).isEqualTo(3);
    }

    @Test
    public void shouldCalcSize()
    {
        loopingList.remove(2);
        loopingList.remove(1);

        assertThat(loopingList.size()).isEqualTo(1);
    }


    @Test
    public void shouldWorkWith1Elem()
    {
        loopingList.remove(2);
        loopingList.remove(1);

        assertThat(loopingList.next()).isEqualTo(3);
        assertThat(loopingList.next()).isEqualTo(3);
    }


    @Test
    public void shouldMoveThePointerWhenItsElemRemoved()
    {
        loopingList.assignAWalker(1);
        loopingList.remove(1);

        assertThat(loopingList.getCurrent()).isEqualTo(3);
        assertThat(loopingList.next()).isEqualTo(2);
    }
}
