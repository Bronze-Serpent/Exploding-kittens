package com.kittens.server.game.initialization.configs;

import com.kittens.logic.combination.Combination;
import com.kittens.logic.service.*;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationPropertiesScan
@Configuration
public class GameStateUtilsConfig {

    @Bean
    public GameStateUtils gameStateUtils(List<Combination> combinations) {
        return new GameStateUtils(cardHandler(), combinationHandler(combinations));
    }

    @Bean
    public CardHandler cardHandler() {
        return new CardHandlerImpl();
    }

    @Bean
    public CombinationHandler combinationHandler(List<Combination> combinations) {
        return new CombinationHandlerImpl(combinations);
    }
}
