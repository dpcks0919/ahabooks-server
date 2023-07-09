drop table if exists evaluation;
create table evaluation
(
    evaluation_id     bigint       not null
        primary key auto_increment,
    record_id         bigint       not null,
    expert_evaluation varchar(255) null,
    misreading_rate   int          null,
    created_at        datetime(6)  null,
    modified_at       datetime(6)  null,
    constraint FK5naqm66ljyw6e229pggqk2mpq
        foreign key (record_id) references kbook.record (record_id)
);

