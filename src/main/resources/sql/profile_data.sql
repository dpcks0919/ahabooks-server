DELETE FROM `profile`;
insert into `profile` (profile_id, image_url, birth_date, gender, name, recommendation_step_id, created_at, modified_at)
VALUES (1, 'image1', now(), 'MALE', 'account1-profile1', 1, now(), now()),
       (2, 'image2', now(), 'FEMALE', 'account1-profile2', 2, now(), now()),
       (3, 'image3', now(), 'MALE', 'account1-profile3', 3, now(), now()),
       (4, 'image4', now(), 'FEMALE', 'account2-profile1', 1, now(), now()),
       (5, 'image5', now(), 'MALE', 'account2-profile2', 2, now(), now())
;