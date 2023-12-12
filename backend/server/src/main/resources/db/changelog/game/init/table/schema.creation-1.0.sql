--liquibase formatted sql


--changeset barabanov:action
CREATE TABLE action
(
    id              SERIAL              PRIMARY KEY,
    name            VARCHAR(60)         NOT NULL        UNIQUE
);


--changeset barabanov:sudden_action
CREATE TABLE sudden_action
(
    id              SERIAL              PRIMARY KEY,
    name            VARCHAR(60)         NOT NULL        UNIQUE
);


--changeset barabanov:card
CREATE TABLE card
(
    id                  SERIAL              PRIMARY KEY,
    name                VARCHAR(60)         NOT NULL        UNIQUE,
    getting_action_id   INT                                         REFERENCES action (id) ON DELETE RESTRICT,
    playing_action_id   INT                                         REFERENCES action (id) ON DELETE RESTRICT,
    sudden_action_id    INT                                         REFERENCES sudden_action (id) ON DELETE RESTRICT
);


--changeset barabanov:combination
CREATE TABLE combination
(
    id              SERIAL              PRIMARY KEY,
    name            VARCHAR(60)         NOT NULL        UNIQUE,
    action_id       INT                                         REFERENCES action (id) ON DELETE RESTRICT,
    predicate       VARCHAR(60)	        NOT NULL        UNIQUE,
    is_enabled      BOOLEAN             NOT NULL
);


--changeset barabanov:player_start_card
-- карты, которые раздаются каждому игроку в указанном количестве
CREATE TABLE player_start_card
(
    id              SERIAL              PRIMARY KEY,
    card_id         INT                 UNIQUE          REFERENCES card (id)  ON DELETE RESTRICT,
    quantity        INT
);


--changeset barabanov:game_card
-- карты, которые добавляются в игру в указанном количестве
CREATE TABLE game_card
(
    id              SERIAL              PRIMARY KEY,
    card_id         INT                 UNIQUE          REFERENCES card (id)  ON DELETE RESTRICT,
    quantity        INT
);
