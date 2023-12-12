--liquibase formatted sql


--changeset barabanov:1
CREATE TABLE player
(
    id              BIGSERIAL                 PRIMARY KEY, -- но есть вопрос. Так у нас не могут повторяться значения. Но на деле нам важно лишь чтобы комбинация room_id +  player_id была уникальна. А так у нас рано или поздно закончатся player_id
    user_id         BIGINT,
    cards           JSONB
);


--changeset barabanov:2
CREATE TABLE game_state
(
    id                 BIGSERIAL              PRIMARY KEY,
    step_quantity      INT,
    now_turn_id        INT                                 REFERENCES player (id)
);


--changeset barabanov:3
CREATE TABLE card_deck
(
    id              SERIAL        PRIMARY KEY,
    game_state_id   INT           UNIQUE                 REFERENCES game_state (id)  ON DELETE CASCADE,
    value           JSONB
);


--changeset barabanov:4
CREATE TABLE card_reset
(
    id              SERIAL        PRIMARY KEY,
    game_state_id   INT           UNIQUE                 REFERENCES game_state (id)  ON DELETE CASCADE,
    value           JSONB
);


--changeset barabanov:5
CREATE TABLE player_queue_pointer
(
    id                       SERIAL        PRIMARY KEY,
    game_state_id            INT                            REFERENCES game_state (id)  ON DELETE CASCADE,
    pointing_player_id       INT                            REFERENCES player (id),
    pointed_at_player_id     INT                            REFERENCES player (id),
    CONSTRAINT unique_pointing_player_in_game UNIQUE (game_state_id, pointing_player_id),
    CONSTRAINT unique_pointed_at_player_in_game UNIQUE (game_state_id, pointed_at_player_id)
);
