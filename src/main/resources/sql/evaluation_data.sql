truncate `evaluation`;
insert into `evaluation`(evaluation_id, record_id, expert_evaluation, misreading_rate, created_at, modified_at)
values (1, 1, 'expert1', 10, now(), now()),
       (2, 2, 'expert2', 20, now(), now()),
       (3, 3, 'expert3', 30, now(), now()),
       (4, 4, 'expert4', 40, now(), now()),
       (5, 5, 'expert5', 50, now(), now())
    ;