DELETE FROM `account_profile_relation`;
insert into `account_profile_relation` (account_profile_relation_id, account_id, profile_id, created_at, modified_at)
VALUES (1, 1, 1, now(), now()),
       (2, 1, 2, now(), now()),
       (3, 1, 3, now(), now()),
       (4, 2, 4, now(), now()),
       (5, 2, 5, now(), now())
;