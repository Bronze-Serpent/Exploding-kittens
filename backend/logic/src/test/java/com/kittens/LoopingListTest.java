package com.kittens;

import com.kittens.logic.LoopingList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LoopingListTest
{
    private final List<Long> sourceList = new ArrayList<>(List.of(1L, 2L, 3L));

    private final LoopingList<Long> loopingList = new LoopingList<>(sourceList);

    @Test
    public void shouldSpinTheList()
    {
        assertThat(loopingList.next()).isEqualTo(1L);
        assertThat(loopingList.next()).isEqualTo(2L);
        assertThat(loopingList.next()).isEqualTo(3L);
        assertThat(loopingList.next()).isEqualTo(1L);
    }

    @Test
    public void shouldRemoveElem()
    {
        assertThat(loopingList.next()).isEqualTo(1L);
        assertThat(loopingList.next()).isEqualTo(2L);
        loopingList.remove(2L);
        assertThat(loopingList.next()).isEqualTo(3L);

        assertThat(loopingList.next()).isEqualTo(1L);
        assertThat(loopingList.next()).isEqualTo(3L);
    }

    @Test
    public void shouldCalcSize()
    {
        loopingList.remove(2L);
        loopingList.remove(1L);

        assertThat(loopingList.size()).isEqualTo(1);
    }
}
