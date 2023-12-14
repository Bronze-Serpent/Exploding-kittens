package com.kittens.server.game.initialization.configs;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.combination.Combination;
import com.kittens.logic.combination.OrdinaryCombination;
import com.kittens.server.game.initialization.entity.CardEntity;
import com.kittens.server.game.initialization.entity.CombinationEntity;
import com.kittens.server.game.initialization.mapper.ActionEntityToAction;
import com.kittens.server.game.initialization.mapper.SudActionEntityToSudAction;
import com.kittens.server.game.initialization.repository.CardRepository;
import com.kittens.server.game.initialization.repository.CombinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Configuration
public class GameCardCombinationConfig
{

    private final CardRepository cardRepository;
    private final CombinationRepository combinationRepository;

    private final ActionEntityToAction actionEntityMapper;
    private final SudActionEntityToSudAction sudActionEntityMapper;


    @Bean
    public List<Combination> combinations()
    {
        List<CombinationEntity> allCombinationsEntity = combinationRepository.findAll();

        List<Combination> combinations = new ArrayList<>();
        for (CombinationEntity combinationEntity : allCombinationsEntity)
        {
            combinations.add(new OrdinaryCombination(
                    combinationEntity.getName(),
                    combinationEntity.getCombPredicate().getPredicate(),
                    actionEntityMapper.map(combinationEntity.getAction())
            ));
        }

        return combinations;
    }


    @Bean
    public List<Card> cards()
    {
        List<CardEntity> allCardsEntity = cardRepository.findAll();

        List<Card> cards = new ArrayList<>();
        for (CardEntity cardEntity : allCardsEntity)
        {
            cards.add(new OrdinaryCard(
                    cardEntity.getName(),
                    actionEntityMapper.map(cardEntity.getGettingAction()),
                    actionEntityMapper.map(cardEntity.getPlayingAction()),
                    sudActionEntityMapper.map(cardEntity.getSuddenAction())
            ));
        }

        return cards;
    }
}
