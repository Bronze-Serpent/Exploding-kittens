--liquibase formatted sql

--changeset barabanov:action
INSERT INTO action (id, name)
VALUES (1,  'explode_or_defuse'),
       (2,  'inaction'),
       (3,  'peek'),
       (4,  'shuffle'),
       (5,  'skipping_move'),
       (6,  'steal_card_from_reset'),
       (7,  'steal_known_card'),
       (8,  'steal_of_player_choice'),
       (9,  'steal_unknown_card'),
       (10, 'transferring_move');


--changeset barabanov:sudden_action
INSERT INTO sudden_action (id, name)
VALUES  (1, 'cancel'),
        (2, 'sudden_inaction');


--changeset barabanov:card
INSERT INTO card (id, name, getting_action_id, playing_action_id, sudden_action_id)
VALUES  (1,  'EXPLODING_KITTEN',     1,             2,               2),
        (2,  'DEFUSE',               2,             2,               2),
        (3,  'ATTACK',               2,             10,              2),
        (4,  'FAVOR',                2,             8,               2),
        (5,  'NO',                   2,             2,               1),
        (6,  'GET_LOST',             2,             5,               2),
        (7,  'SHUFFLE',              2,             4,               2),
        (8,  'SEE_THE_FUTURE',       2,             3,               2),
        (9,  'HAIRY_CATATO',         2,             2,               2),
        (10, 'TACOCAT',              2,             2,               2),
        (11, 'BEARDCAT',             2,             2,               2),
        (12, 'NYAN_CAT',             2,             2,               2),
        (13, 'CATTERMELON',          2,             2,               2);


--changeset barabanov:combination
INSERT INTO combination (id, name,                  action_id,       predicate,            is_enabled)
VALUES                  (1, 'steal_unknown_card',       9,           'TWO_IDENTICAL',        true),
                        (2, 'steal_known_card',         7,           'THREE_IDENTICAL',      true),
                        (3, 'steal_from_reset',         6,           'FIVE_DIFFERENT',       true);


--changeset barabanov:player_start_card
INSERT INTO player_start_card (card_id, quantity)
VALUES  (2, 1);


--changeset barabanov:game_card
INSERT INTO game_card (card_id, quantity)
VALUES                  (1,         4),
                        (2,         6),
                        (3,         4),
                        (4,         4),
                        (5,         5),
                        (6,         4),
                        (7,         4),
                        (8,         5),
                        (9,         4),
                        (10,        4),
                        (11,        4),
                        (12,        4),
                        (13,        4);

