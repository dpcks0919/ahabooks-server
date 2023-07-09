DELETE FROM `account_payment_log`;
insert into `account_payment_log` (account_payment_log_id, payment_account, subscription_status, account_id, created_at, modified_at)
values (1, 5000, 'MONTHLY', 1, now(), now()),
       (2, 5000, 'MONTHLY', 2, now(), now()),
       (3, 50000, 'YEARLY', 3, now(), now())
;