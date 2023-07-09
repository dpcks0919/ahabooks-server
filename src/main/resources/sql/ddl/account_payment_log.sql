drop table if exists account_payment_log;
create table account_payment_log
(
    account_payment_log_id bigint       not null
        primary key auto_increment,
    account_id             bigint       null,
    payment_account        int          null,
    subscription_status    varchar(255) null,
    created_at             datetime(6)  null,
    modified_at            datetime(6)  null,
    constraint FKb8m4ffnr23h56qfu614pb3mph
        foreign key (account_id) references kbook.account (account_id)
);

