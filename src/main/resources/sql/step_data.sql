DELETE FROM `step`;
INSERT INTO `step`(step_id, name, description, created_at, modified_at)
VALUES (1, '1', 'step1 입니다', now(), now()),
       (2, '2', 'step2 입니다', now(), now()),
       (3, '3', 'step3 입니다', now(), now())
;