package com.kittens.logic;

import com.kittens.logic.model.LoopingListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class LoopingListImplTest
{
    private final List<Integer> sourceList = new ArrayList<>(List.of(1, 2, 3));

    private final LoopingListImpl<Integer> loopingList = new LoopingListImpl<>(sourceList);

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


    @Test
    public void shouldInitByMap()
    {
        Map<Integer, Integer> sourceMap = Map.of(
                2, 3,
                1, 2,
                3, 1
        );

        LoopingListImpl<Integer> loopingList = new LoopingListImpl<>(sourceMap);

        assertThat(loopingList.getElements()).containsExactlyInAnyOrder(1, 2, 3);
    }


    @Test
    public void shouldNotInitLoopingList()
    {
        Map<Integer, Integer> sourceMap = Map.of(
                1, 2,
                2, 2
        );

        Assertions.assertThrows(RuntimeException.class, () -> new LoopingListImpl<>(sourceMap));

    }


    @Test
    public void shouldNotInitLoopingList2()
    {
        Map<Integer, Integer> sourceMap = Map.of(
                1, 2,
                2, 1,
                3, 4
        );

        Assertions.assertThrows(RuntimeException.class, () -> new LoopingListImpl<>(sourceMap));

    }
}
