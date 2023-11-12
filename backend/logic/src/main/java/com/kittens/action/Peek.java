package com.kittens.action;

import com.kittens.GameState;
import com.kittens.card.Card;
import com.kittens.action.player.interaction.PlayerInformer;
import com.kittens.card.CardName;
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
        Long playerId = gameState.getNowTurn().getId();

        switch ((3 <= deckSize) ? 3 :
                (deckSize == 2) ? 2 :
                (deckSize == 1) ? 1 : 0)
        {
            case 3 -> informPlayer(playerId, cardDeck.subList(cardDeck.size() - 3, cardDeck.size()));
            case 2 -> informPlayer(playerId, cardDeck.subList(0, 2));
            case 1 -> informPlayer(playerId, cardDeck.subList(0, 1));
            case 0 -> informPlayer(playerId, Collections.emptyList());
        }
    }

    @Override
    public String getName() {
        return "peek";
    }


    private void informPlayer(Long playerId, List<Card> cards)
    {
        playerInformer.inform(playerId,
                            PlayerInformer.Informing.SHOW_CARDS,
                            cards.stream()
                                    .map(Card::getName)
                                    .map(CardName::getWriting)
                                    .limit(3)
                                    .collect(Collectors.joining(","))
        );
    }
}
