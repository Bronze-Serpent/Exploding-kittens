package com.kittens.server.mapper;

import com.kittens.logic.action.Action;
import com.kittens.server.entity.game.init.ActionEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ActionEntityToAction implements Mapper<ActionEntity, Action>
{
    private final Map<String, Action> actionMap = new HashMap<>();

    public ActionEntityToAction(List<Action> actions)
    {
        for (Action action : actions)
            this.actionMap.put(action.getName(), action);
    }

    @Override
    public Action map(ActionEntity object) {
        return actionMap.get(object.getName());
    }
}
