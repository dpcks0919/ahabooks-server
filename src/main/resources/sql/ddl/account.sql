drop table if exists account;
create table account
(
    account_id            bigint       not null
        primary key,
    name                  varchar(255) null,
    email                 varchar(255) null,
    phone                 varchar(255) null,
    account_type          varchar(255) null,
    subscription_status   varchar(255) null,
    subscription_deadline datetime(6)  null,
    role                  varchar(255) null,
    created_at            datetime(6)  null,
    modified_at           datetime(6)  null
);