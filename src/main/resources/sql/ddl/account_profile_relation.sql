drop table if exists account_profile_relation;
create table account_profile_relation
(
    account_profile_relation_id bigint      not null
        primary key auto_increment,
    account_id                  bigint      null,
    profile_id                  bigint      null,
    created_at                  datetime(6) null,
    modified_at                 datetime(6) null,
    constraint FK3t3y0hkx0y9q77cuerfrs6ra7
        foreign key (profile_id) references kbook.profile (profile_id),
    constraint FKspxkobiacnnlp64v3agrxkhu1
        foreign key (account_id) references kbook.account (account_id)
);

