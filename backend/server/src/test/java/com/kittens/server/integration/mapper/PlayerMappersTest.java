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
import java.util.Optional;

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
      UserRefPlayer modifiedPlayer = new UserRefPlayer(1L, cards, null);

      PlayerEntity player = playerRepository.findById(1L).get();
      playerToPlayerEntity.copy(modifiedPlayer, player);

      assertThat(player.getId()).isEqualTo(modifiedPlayer.getId());
      assertThat(player.getUser().getId()).isEqualTo(modifiedPlayer.getId());
      assertThat(player.getCards()).containsExactlyElementsOf(cards.stream()
              .map(Card::getName)
              .toList());
   }



}
