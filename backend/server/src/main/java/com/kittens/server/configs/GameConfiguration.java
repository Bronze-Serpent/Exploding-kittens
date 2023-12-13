package com.kittens.server.configs;

import com.kittens.logic.action.*;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.action.player.interaction.PlayerQuestioner;
import com.kittens.logic.action.sudden.Cancel;
import com.kittens.logic.action.sudden.SuddenAction;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.logic.combination.Combination;
import com.kittens.logic.combination.CombinationPredicate;
import com.kittens.logic.combination.OrdinaryCombination;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// TODO: 13.12.2023 Action создаются просто бинами.
//  Card и Combination бины должены создаваться на основании запроса к БД.
//  Получить все CardEntity и на их основании создать бины, используя Action

@RequiredArgsConstructor
@Configuration
public class GameConfiguration
{

    private final PlayerQuestioner playerQuestioner;

    private final PlayerInformer playerInformer;

    /*
    Combinations
     */
    @Bean
    public Combination stealUnknownCardComb()
    {
        return new OrdinaryCombination("steal_unknown_card",
                                        CombinationPredicate.TWO_IDENTICAL.getPredicate(),
                                        stealUnknownCardAction()
        );
    }

    @Bean
    public Combination stealKnownCardComb()
    {
        return new OrdinaryCombination("steal_known_card",
                                        CombinationPredicate.THREE_IDENTICAL.getPredicate(),
                                        stealKnownCardAction()
        );
    }

    @Bean
    public Combination stealFromResetComb()
    {
        return new OrdinaryCombination("steal_from_reset",
                                        CombinationPredicate.FIVE_DIFFERENT.getPredicate(),
                                        stealCardFromResetAction()
        );
    }


    // TODO: 13.12.2023 Это Action мы кладём через @Bean, но Карты и Комбинации мы должны клатсь во время работы программы, считывая их из описание из БД
    /*
    Cards
     */
    @Bean
    public Card explodingKittenCard()
    {
        return new OrdinaryCard(CardName.EXPLODING_KITTEN,
                                explodeOrDefuseAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card defuseCard()
    {
        return new OrdinaryCard(CardName.DEFUSE,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card attackCard()
    {
        return new OrdinaryCard(CardName.ATTACK,
                                inactionAction(),
                                transferringMoveAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card favorCard()
    {
        return new OrdinaryCard(CardName.FAVOR,
                                inactionAction(),
                                stealOfPlayerChoiceAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card noCard()
    {
        return new OrdinaryCard(CardName.NO,
                                inactionAction(),
                                inactionAction(),
                                cancelSuddenAction());
    }

    @Bean
    public Card getLostCard()
    {
        return new OrdinaryCard(CardName.GET_LOST,
                                inactionAction(),
                                skippingMoveAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card shuffleCard()
    {
        return new OrdinaryCard(CardName.SHUFFLE,
                                inactionAction(),
                                shuffleAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card seeTheFutureCard()
    {
        return new OrdinaryCard(CardName.SEE_THE_FUTURE,
                                inactionAction(),
                                peekAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card hairyCatatoCard()
    {
        return new OrdinaryCard(CardName.HAIRY_CATATO,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card tacocatCard()
    {
        return new OrdinaryCard(CardName.TACOCAT,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card beadcatCard()
    {
        return new OrdinaryCard(CardName.BEARDCAT,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card nyahCatCard()
    {
        return new OrdinaryCard(CardName.NYAN_CAT,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }

    @Bean
    public Card cattermelonCard()
    {
        return new OrdinaryCard(CardName.CATTERMELON,
                                inactionAction(),
                                inactionAction(),
                                inactionSuddenAction());
    }


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
