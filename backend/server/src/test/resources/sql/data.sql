INSERT INTO player (id)
VALUES (1),
       (2),
       (3);


INSERT INTO game_state (id, step_quantity, now_turn_id)
VALUES (1, 1, 1);


INSERT INTO card_info (id, card_id, quantity)
VALUES (1, 3, 2),
       (2, 10, 1),
       (3, 11, 1),
       (4, 6, 3),
       (5, 1, 2),
       (6, 2, 4);


INSERT INTO player_card (id, player_id, card_info_id)
VALUES (1, 1, 2),
       (2, 1, 3),
       (3, 2, 4);

INSERT INTO card_deck (id, game_state_id, card_info_id)
VALUES (1, 1, 1),
       (2, 1, 5);


INSERT INTO card_reset (id, game_state_id, card_info_id)
VALUES (1, 1, 6);


INSERT INTO player_queue_pointer (id, game_state_id, pointing_player_id, pointed_at_player_id)
VALUES (1, 1, 1, 2),
       (2, 1, 2, 3),
       (3, 1, 3, 1);
-- SELECT SETVAL('company_id_seq', (SELECT MAX(id) FROM company));
