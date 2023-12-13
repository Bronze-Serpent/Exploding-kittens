--liquibase formatted sql


--changeset barabanov:player
CREATE TABLE player
(
    id              BIGSERIAL                 PRIMARY KEY, -- но есть вопрос. Так у нас не могут повторяться значения. Но на деле нам важно лишь чтобы комбинация room_id +  player_id была уникальна. А так у нас рано или поздно закончатся player_id
    user_id         BIGINT                                  REFERENCES user_account (id),
    cards           VARCHAR []
);


--changeset barabanov:card_deck
CREATE TABLE card_deck
(
    id              BIGSERIAL        PRIMARY KEY,
    value           VARCHAR []
);


--changeset barabanov:card_reset
CREATE TABLE card_reset
(
    id              BIGSERIAL        PRIMARY KEY,
    value           VARCHAR []
);


--changeset barabanov:game_state
CREATE TABLE game_state
(
    id                 BIGSERIAL              PRIMARY KEY,
    step_quantity      INT,
    now_turn_id        BIGINT                                  REFERENCES player (id),
    card_deck_id       BIGINT                                  REFERENCES card_deck (id) ON DELETE RESTRICT,
    card_reset_id      BIGINT                                  REFERENCES card_reset (id) ON DELETE RESTRICT
);


--changeset barabanov:player_queue_pointer
CREATE TABLE player_queue_pointer
(
    id                       BIGSERIAL        PRIMARY KEY,
    game_state_id            BIGINT                            REFERENCES game_state (id)  ON DELETE CASCADE,
    pointing_player_id       BIGINT                            REFERENCES player (id),
    pointed_at_player_id     BIGINT                            REFERENCES player (id),
    CONSTRAINT unique_pointing_player_in_game UNIQUE (game_state_id, pointing_player_id),
    CONSTRAINT unique_pointed_at_player_in_game UNIQUE (game_state_id, pointed_at_player_id)
);
