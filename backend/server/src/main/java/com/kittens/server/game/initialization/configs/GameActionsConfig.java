package com.kittens.server.game.initialization.configs;

import com.kittens.logic.action.*;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.action.sudden.Cancel;
import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.logic.action.sudden.SuddenInaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// бины с Action вынесены в отдельный класс чтобы не было циклической зависимости GameConfig -> Mapper -> бины Action внутри GameConfig
@RequiredArgsConstructor
@Configuration
public class GameActionsConfig
{

    private final PlayerQuestioner playerQuestioner;
    private final PlayerInformer playerInformer;


    /*
    Actions
     */
    @Bean
    public Action explodeOrDefuseAction() { return new ExplodeOrDefuse(playerQuestioner, playerInformer);}

    @Bean
    public Action inactionAction() { return new Inaction(); }

    @Bean
    public Action peekAction() { return new Peek(playerInformer); }

    @Bean
    public Action shuffleAction() { return new Shuffle(playerQuestioner); }

    @Bean
    public Action skippingMoveAction() { return new SkippingMove(); }

    @Bean
    public Action stealCardFromResetAction() { return new StealCardFromReset(playerQuestioner, playerInformer); }

    @Bean
    public Action stealKnownCardAction() { return new StealKnownCard(playerQuestioner, playerInformer); }

    @Bean
    public Action stealOfPlayerChoiceAction() { return new StealOfPlayerChoice(playerQuestioner, playerInformer); }

    @Bean
    public Action stealUnknownCardAction() { return new StealUnknownCard(playerQuestioner, playerInformer); }

    @Bean
    public Action transferringMoveAction() { return new TransferringMove(); }


    /*
    Sudden actions
     */
    @Bean
    public SuddenAction cancelSuddenAction() { return new Cancel(); }

    @Bean
    public SuddenAction inactionSuddenAction() { return new SuddenInaction(); }
}
