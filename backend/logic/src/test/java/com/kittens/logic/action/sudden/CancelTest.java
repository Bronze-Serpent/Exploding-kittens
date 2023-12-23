package com.kittens.logic.action.sudden;

import com.kittens.logic.action.Action;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.TransferringMove;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CancelTest
{
    private final SuddenAction cancel = new Cancel();

    @Test
    public void shouldCancelAction()
    {
        Action action1 = new TransferringMove();
        Action action2 = new SkippingMove();

        var resultAction = cancel.doSuddenAction(action1, action2);

        assertThat(resultAction).isEqualTo(action1);
    }
}
