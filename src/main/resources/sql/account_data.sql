DELETE FROM `account`;
INSERT INTO `account`(account_id, account_type, email, name, phone, role, subscription_deadline, subscription_status, created_at, modified_at, is_vulnerable)
VALUES (1,'KAKAO','email11.com','name11','phone11','ADMIN',now(),'MONTHLY', now(), now(), false),
       (2,'KAKAO','email22.com','name22','phone22','USER',now(),'YEARLY', now(), now(), false),
       (3,'KAKAO','email33.com','name33','phone33','USER', null,'UNSUBSCRIBE', now(), now(), false)
;