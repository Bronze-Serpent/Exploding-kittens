package com.kittens.server.game.initialization.mapper;

import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.server.game.initialization.entity.SuddenActionEntity;
import com.kittens.server.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class SudActionEntityToSudAction implements Mapper<SuddenActionEntity, SuddenAction>
{
    private final Map<String, SuddenAction> actionMap = new HashMap<>();

    public SudActionEntityToSudAction(List<SuddenAction> actions)
    {
        for (SuddenAction action : actions)
        {
            this.actionMap.put(action.getName(), action);
        }
    }

    @Override
    public SuddenAction map(SuddenActionEntity object) {
        return actionMap.get(object.getName());
    }
}
