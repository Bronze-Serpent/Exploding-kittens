INSERT INTO user_account (id, login, password_hash)
VALUES (1, 'user1', 1),
       (2, 'user2', 3);
SELECT SETVAL('user_account_id_seq', (SELECT MAX(id) FROM user_account));


INSERT INTO player (id, user_id, cards)
VALUES (1, 1, '{"DEFUSE", "FAVOR", "HAIRY_CATATO"}'),
       (2, 1, '{}'),
       (3, 2, '{}');
SELECT SETVAL('player_id_seq', (SELECT MAX(id) FROM player));


INSERT INTO card_deck (id, value)
VALUES (1, '{"TACOCAT", "BEARDCAT", "NYAN_CAT", "DEFUSE"}');
SELECT SETVAL('card_deck_id_seq', (SELECT MAX(id) FROM card_deck));


INSERT INTO card_reset (id, value)
VALUES (1, '{}');
SELECT SETVAL('card_reset_id_seq', (SELECT MAX(id) FROM card_reset));


INSERT INTO game_state (id, step_quantity, now_turn_id, card_deck_id, card_reset_id)
VALUES (1, 1, 1, 1, 1);
SELECT SETVAL('game_state_id_seq', (SELECT MAX(id) FROM game_state));


INSERT INTO player_queue_pointer (id, game_state_id, pointing_player_id, pointed_at_player_id)
VALUES (1, 1, 1, 2),
       (2, 1, 2, 3),
       (3, 1, 3, 1);
SELECT SETVAL('player_queue_pointer_id_seq', (SELECT MAX(id) FROM player_queue_pointer));
