package com.kittens.server.service;

import com.kittens.server.dto.EndYouTurnDto;
import com.kittens.server.dto.PlayCardDto;
import com.kittens.server.dto.PlayCombinationDto;


public interface GameService
{
    void endYourTurn(Long roomId, EndYouTurnDto endYouTurnDto);

    void playCard(Long roomId, PlayCardDto playCardDto);

    void playCombination(Long roomId, PlayCombinationDto playCombinationDto);

    void initGameInRoom(Long roomId);
}
