package com.kittens.server.integration.mapper;

import com.kittens.logic.action.Inaction;
import com.kittens.logic.action.SkippingMove;
import com.kittens.logic.action.sudden.SuddenInaction;
import com.kittens.logic.card.Card;
import com.kittens.logic.card.CardName;
import com.kittens.logic.card.OrdinaryCard;
import com.kittens.server.entity.PlayerEntity;
import com.kittens.server.game.model.UserRefPlayer;
import com.kittens.server.integration.IntegrationTest;
import com.kittens.server.mapper.PlayerEntityToUserRefPlayer;
import com.kittens.server.mapper.UserRefPlayerToPlayerEntity;
import com.kittens.server.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
public class PlayerMappersTest extends IntegrationTest
{
   private final PlayerEntityToUserRefPlayer playerEntityToPlayer;

   private final UserRefPlayerToPlayerEntity playerToPlayerEntity;

   private final PlayerRepository playerRepository;


   @Test
   public void shouldMapEntityToPlayer()
   {
      PlayerEntity playerEntity = playerRepository.findById(1L).get();

      UserRefPlayer player = playerEntityToPlayer.map(playerEntity);

      assertThat(player.getUserId()).isEqualTo(1L);
      assertThat(player.getUserId()).isEqualTo(1L);
      assertThat(player.getCards().stream()
              .map(Card::getName)
              .map(CardName::toString)
              .toList()).containsExactlyInAnyOrder("DEFUSE", "FAVOR", "HAIRY_CATATO");
   }


   @Test
   public void shouldMapPlayerToEntity()
   {
      List<Card> cards = CreationUtils.createCards();
      UserRefPlayer userRefPlayer = new UserRefPlayer(1L, cards, 1L);

      PlayerEntity playerEntity = playerToPlayerEntity.map(userRefPlayer);
      assertThat(playerEntity.getId()).isEqualTo(userRefPlayer.getId());
      assertThat(playerEntity.getUser().getId()).isEqualTo(userRefPlayer.getId());
      assertThat(playerEntity.getCards()).containsExactlyElementsOf(cards.stream()
              .map(Card::getName)
              .toList());
   }



}
