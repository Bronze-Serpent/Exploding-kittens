package com.kittens.server.game.initialization.configs;

import com.kittens.logic.card.CardName;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "game.property")
public record GameSettingsProperties
        (
                int numOfCardsPlayersHave,
                List<CardDesc> playerStartCards,
                List<CardDesc> cardsInGame
        )
{
    public record CardDesc(
            CardName name,
            int quantity
    ) {}
}
