package com.kittens.logic.action;

import com.kittens.logic.card.Card;
import com.kittens.logic.action.player.interaction.PlayerInformer;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.GameState;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class Peek implements Action
{

    private final PlayerInformer playerInformer;


    @Override
    public void doAction(GameState gameState)
    {
        List<Card> cardDeck = gameState.getCardDeck();
        int deckSize = cardDeck.size();
        AbstractPlayer player = gameState.getNowTurn();

        switch ((3 <= deckSize) ? 3 :
                (deckSize == 2) ? 2 :
                (deckSize == 1) ? 1 : 0)
        {
            case 3 -> informPlayer(player, cardDeck.subList(cardDeck.size() - 3, cardDeck.size()));
            case 2 -> informPlayer(player, cardDeck.subList(0, 2));
            case 1 -> informPlayer(player, cardDeck.subList(0, 1));
            case 0 -> informPlayer(player, Collections.emptyList());
        }
    }

    @Override
    public String getName() {
        return "peek";
    }


    private void informPlayer(AbstractPlayer player, List<Card> cards)
    {
        playerInformer.inform(player,
                            PlayerInformer.Informing.SHOW_CARDS,
                            cards.stream()
                                    .map(Card::getName)
                                    .map(CardName::getWriting)
                                    .limit(3)
                                    .collect(Collectors.joining(","))
        );
    }
}
