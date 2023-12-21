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

--changeset barabanov:card_name_enum
CREATE TYPE card_name_type
AS ENUM (
    'EXPLODING_KITTEN',
    'DEFUSE',
    'ATTACK',
    'FAVOR',
    'NO',
    'GET_LOST',
    'SHUFFLE',
    'SEE_THE_FUTURE',
    'HAIRY_CATATO',
    'TACOCAT',
    'BEARDCAT',
    'NYAN_CAT',
    'CATTERMELON'
    );


--changeset barabanov:card
CREATE TABLE card
(
    id                  SERIAL              PRIMARY KEY,
    name                card_name_type      NOT NULL        UNIQUE,
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
