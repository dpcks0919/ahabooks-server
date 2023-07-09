DELETE FROM `record`;
INSERT INTO `record`(record_id, profile_book_relation_id, round, evaluated, page, time, created_at, modified_at)
VALUES (1, 1, 1, true, 0, 200, now(), now()),
       (2, 1, 2, true, 0, 300, now(), now()),
       (3, 2, 1, false, 0, 250, now(), now()),
       (4, 2, 2, false, 0, 350, now(), now()),
       (5, 7, 1, false, 1, 20, now(), now()),
       (6, 7, 1, false, 2, 25, now(), now())
;


