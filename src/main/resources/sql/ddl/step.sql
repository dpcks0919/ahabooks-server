drop table if exists step;
create table step
(
    step_id     bigint       not null
        primary key auto_increment,
    name        varchar(255) null,
    description varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);

