--liquibase formatted sql


--changeset barabanov:1
CREATE TABLE player
(
    id              INT                 PRIMARY KEY
);


--changeset barabanov:2
CREATE TABLE game_state
(
    id                 SERIAL              PRIMARY KEY,
    step_quantity      INT,
    now_turn_id        INT                                 REFERENCES player (id)
);


--changeset barabanov:3
CREATE TABLE card_info
(
    id              SERIAL              PRIMARY KEY,
    card_id         INT                 UNIQUE          REFERENCES card (id)  ON DELETE RESTRICT,
    quantity        INT
);


--changeset barabanov:4
CREATE TABLE player_card
(
    id              SERIAL        PRIMARY KEY,
    player_id       INT                                 REFERENCES player (id)  ON DELETE CASCADE,
    card_info_id    INT                                 REFERENCES card_info (id)  ON DELETE CASCADE,
    CONSTRAINT player_card_info_ids UNIQUE (player_id, card_info_id)
);


--changeset barabanov:5
CREATE TABLE card_deck
(
    id              SERIAL        PRIMARY KEY,
    game_state_id   INT                                 REFERENCES game_state (id)  ON DELETE CASCADE,
    card_info_id    INT                                 REFERENCES card_info (id)  ON DELETE CASCADE,
    CONSTRAINT deck_game_state_card_ids UNIQUE (game_state_id, card_info_id)
);


--changeset barabanov:6
CREATE TABLE card_reset
(
    id              SERIAL        PRIMARY KEY,
    game_state_id   INT                                 REFERENCES game_state (id)  ON DELETE CASCADE,
    card_info_id    INT                                 REFERENCES card_info (id)  ON DELETE CASCADE,
    CONSTRAINT reset_game_state_card_ids UNIQUE (game_state_id, card_info_id)
);


--changeset barabanov:7
CREATE TABLE player_queue_pointer
(
    id                       SERIAL        PRIMARY KEY,
    game_state_id            INT                            REFERENCES game_state (id)  ON DELETE CASCADE,
    pointing_player_id       INT           UNIQUE           REFERENCES player (id),
    pointed_at_player_id     INT                            REFERENCES player (id)
);
