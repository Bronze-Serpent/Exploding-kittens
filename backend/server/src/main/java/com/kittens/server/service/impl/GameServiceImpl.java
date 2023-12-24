package com.kittens.server.service.impl;

import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.model.AbstractPlayer;
import com.kittens.logic.model.LoopingList;
import com.kittens.logic.model.LoopingListImpl;
import com.kittens.logic.service.GameStateUtils;
import com.kittens.server.dto.CreateEditRoomDto;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.game.initialization.configs.GameSettingsProperties;
import com.kittens.server.game.model.RoomGameState;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.mapper.CardNameToCard;
import com.kittens.server.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void playCard(Long roomId, PlayCardDto dto)
    {
        List<PlayCardDto> playSuddenCards = new ArrayList<>();
        Optional<PlayCardDto> playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, dto);
        while (playSuddenCardDto.isPresent())
        {
            playSuddenCards.add(playSuddenCardDto.get());
            playSuddenCardDto = userQuestioner.askPlayersToPlaySuddenCards(roomId, dto);
        }
        // TODO: 19.12.2023 нам бы тут ошибку кидать, что такой комнаты нет
        RoomGameState gameState = gameStateService.getGameStateByRoomId(roomId).get();

        Card card = cardNameToCardMapper.map(dto.getCardName());

        List<Card> suddenCards = cardNameToCardMapper.map(playSuddenCards
                .stream()
                .map(PlayCardDto::getCardName)
                .toArray(CardName[]::new));

        gameStateUtils.playCard(gameState, card, suddenCards);

        gameStateService.updateGameState(gameState);

        //TODO: Вместо геймстейта отправляем геймстейт + карты игрока
        notificationService.sendMessageToRoom(roomId, gameState);
    }


    @Override
    public void createRoom(Long userId)
    {
        Long createdRoomId = roomService.createEmptyRoom();
        roomService.addUserToRoom(createdRoomId, userId);

        // TODO: 21.12.2023 тут нужно что-то сообщать?
        notificationService.sendMessageToUser(createdRoomId, userId,
                new CreateEditRoomDto(createdRoomId, List.of(userId)));
    }


    @Override
    public void addUserToRoom(Long roomId, Long userId)
    {
        roomService.addUserToRoom(roomId, userId);

        List<Long> allUsersId = roomService.getAllUsersId(roomId);
        notificationService.sendMessageToRoom(roomId, allUsersId);
    }


    @Override
    public void initGameStateInRoom(Long roomId)
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

        RoomGameState createdGameState = new RoomGameState(null, null, null, null, 0, emptyGameStateId);
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
