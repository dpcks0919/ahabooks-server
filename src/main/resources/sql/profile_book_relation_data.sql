delete from profile_book_relation;
insert into `profile_book_relation` (profile_book_relation_id, profile_id, book_id,  last_listen_page, last_read_page, listening, reading, views,  created_at, modified_at)
values (1, 1, 1, 1, 1,  false, false, 10, now(), now()),
       (2, 1, 2, 0, 0,  false, true, 20, now(), now()),
       (3, 1, 3, 2, 2,  true, true, 30, now(), now()),
       (4, 2, 1, 1, 1,  true, true, 40, now(), now()),
       (5, 2, 2, 10, 10,  true, true, 50, now(), now()),
       (6, 3, 3, 0, 0,  false, false, 60, now(), now()),
       (7, 4, 4, 2, 2,  true, true, 70, now(), now())
;