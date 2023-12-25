package com.kittens.server.service.impl;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.logic.service.GameStateUtils;
import com.kittens.server.dto.EndYouTurnDto;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.dto.PlayCombinationDto;
import com.kittens.server.game.initialization.configs.GameSettingsProperties;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.mapper.CardNameToCard;
import com.kittens.server.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameServiceImpl implements GameService
{

    NotificationService notificationService;
    GameStateService gameStateService;
    RoomService roomService;
    PlayerService playerService;

    UserQuestioner userQuestioner;

    GameStateUtils gameStateUtils;

    CardNameToCard cardNameToCardMapper;

    List<Card> cardBeans;
    GameSettingsProperties gameSettingsProperties;


    @Override
    public void endYourTurn(Long roomId, EndYouTurnDto endYouTurnDto)
    {
        RoomGameState gameState = gameStateService.getGameStateByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Комнаты для GameState с id: " + roomId + " не найдено." ));

        Long whoPlayedId = endYouTurnDto.getPlayerId();
        if (!gameState.getNowTurn().getId().equals(whoPlayedId))
            throw new RuntimeException("Попытка сыграть карту игрока с id: " + whoPlayedId
                    + ", но сейчас очередь игрока с id: " + gameState.getNowTurn().getId());

        gameStateUtils.addNewCardToPlayer(gameState, whoPlayedId);

        //TODO: Вместо геймстейта отправляем геймстейт + карты игрока
        notificationService.sendMessageToRoom(roomId, gameState);
    }


    @Override
    public void playCombination(Long roomId, PlayCombinationDto playCombinationDto)
    {
        RoomGameState gameState = gameStateService.getGameStateByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Комнаты для GameState с id: " + roomId + " не найдено." ));

        Long whoPlayedId = playCombinationDto.getPlayerId();
        if (!gameState.getNowTurn().getId().equals(whoPlayedId))
            throw new RuntimeException("Попытка сыграть комбинацию игрока с id: " + whoPlayedId
                    + ", но сейчас очередь игрока с id: " + gameState.getNowTurn().getId());

        gameStateUtils.playCombination(
                gameState,
                whoPlayedId,
                playCombinationDto.getCardNames()
                        .stream()
                        .map(cardNameToCardMapper::map)
                        .collect(Collectors.toList())
        );

        updateGame(gameState);

        //TODO: Вместо геймстейта отправляем геймстейт + карты игрока
        notificationService.sendMessageToRoom(roomId, gameState);
    }


    @Override
    public void playCard(Long roomId, PlayCardDto playCardDto)
    {
        RoomGameState gameState = gameStateService.getGameStateByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Комнаты для GameState с id: " + roomId + " не найдено." ));

        Long whoPlayedId = playCardDto.getPlayerId();
        if (!gameState.getNowTurn().getId().equals(whoPlayedId))
            throw new RuntimeException("Попытка сыграть карту игрока с id: " + whoPlayedId
                    + ", но сейчас очередь игрока с id: " + gameState.getNowTurn().getId());

        Map<Long, List<Card>> suddenCards = surveyOnSuddenCards(roomId, playCardDto);
        Card card = cardNameToCardMapper.map(playCardDto.getCardName());
        gameStateUtils.playCard(gameState, whoPlayedId, card, suddenCards);

        updateGame(gameState);

        //TODO: Вместо геймстейта отправляем геймстейт + карты игрока
        notificationService.sendMessageToRoom(roomId, gameState);
    }


    @Override
    public void initGameInRoom(Long roomId)
    {
        List<Long> usersIds = roomService.getAllUsersId(roomId);

        List<AbstractPlayer> players = new ArrayList<>();
        for (Long userId : usersIds)
        {
            Long emptyPlayerId = playerService.createEmptyPlayer(roomId, userId);
            players.add(new UserRefPlayer(emptyPlayerId, new ArrayList<>(), userId));
        }

        LoopingList<AbstractPlayer> loopingList = new LoopingListImpl<>(players);

        List<Card> cards = multiplyCardBins();
        Long emptyGameStateId = gameStateService.createEmptyGameState();

        RoomGameState createdGameState = new RoomGameState(null, null, null, 0, emptyGameStateId);
        gameStateUtils.initGame(
                cards,
                players,
                gameSettingsProperties.numOfCardsPlayersHave(),
                players.get(0),
                createdGameState,
                loopingList
        );

        gameStateService.saveNewGameState(roomId, createdGameState);

        // рассылка user'ам этого gameState
        notificationService.sendMessageToRoom(roomId, createdGameState);
    }



    private void updateGame(RoomGameState gameState)
    {
        gameStateService.updateGameState(gameState);
        playerService.updatePlayers(gameState.getPlayersTurn().getElements()
                .stream()
                .map(abstractPlayer -> (UserRefPlayer) abstractPlayer).collect(Collectors.toSet())
        );
    }


    private Map<Long, List<Card>> surveyOnSuddenCards(Long roomId, PlayCardDto playedCardDto)
    {
        List<PlayCardDto> playSuddenCards = new ArrayList<>();
        Optional<PlayCardDto> playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, playedCardDto);
        while (playSuddenCardDto.isPresent())
        {
            playSuddenCards.add(playSuddenCardDto.get());
            playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, playedCardDto);
        }
        Map<Long, List<Card>> suddenCards = new HashMap<>();
        for (PlayCardDto suddenCardDto : playSuddenCards)
        {
            Long playedBy = suddenCardDto.getPlayerId();
            Card playedCard = cardNameToCardMapper.map(suddenCardDto.getCardName());
            List<Card> playerCards = suddenCards.get(playedBy);
            if (playerCards == null)
                suddenCards.put(playedBy, new ArrayList<>(List.of(playedCard)));
            else
                playerCards.add(playedCard);

        }

        return suddenCards;
    }


    private List<Card> multiplyCardBins()
    {
        Map<CardName, Card> cardNameBeanToCard = cardBeans.stream()
                .collect(Collectors.toMap(Card::getName, card -> card));
        List<Card> cardsInGame = new ArrayList<>();

        for (GameSettingsProperties.CardDesc cardDesc : gameSettingsProperties.cardsInGame())
        {
            for (int quantity = 0; quantity < cardDesc.quantity(); quantity++)
                cardsInGame.add(cardNameBeanToCard.get(cardDesc.name()));
        }
        return cardsInGame;
    }

}
