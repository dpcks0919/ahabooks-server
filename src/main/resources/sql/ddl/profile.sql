drop table if exists profile;
create table profile
(
    profile_id             bigint       not null
        primary key auto_increment,
    name                   varchar(255) null,
    image_url              varchar(255) null,
    birth_date             date         null,
    gender                 varchar(255) null,
    recommendation_step_id bigint       null,
    created_at             datetime(6)  null,
    modified_at            datetime(6)  null,
    constraint FKlv6nyw2mq0wuvbme6p5vmjbmx
        foreign key (recommendation_step_id) references kbook.step (step_id)
);

