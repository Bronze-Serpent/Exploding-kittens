package com.kittens.server.mapper;

import com.kittens.logic.combination.Combination;
import com.kittens.server.game.initialization.entity.CombinationEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CombNameToComb implements Mapper<CombinationEntity, Combination>
{
    private final Map<String, Combination> combNameToComb = new HashMap<>();


    public CombNameToComb(List<Combination> combinations)
    {
        for (Combination combination : combinations)
            combNameToComb.put(combination.getName(), combination);
    }

    @Override
    public Combination map(CombinationEntity object) {
        return combNameToComb.get(object.getName());
    }
}
