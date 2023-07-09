drop table if exists book;
create table book
(
    book_id         bigint       not null
        primary key auto_increment,
    name            varchar(255) null,
    auth            varchar(255) null,
    description     varchar(255) null,
    cover_image_url varchar(255) null,
    epub_file_url   varchar(255) null,
    total_page      int          null,
    type            varchar(255) null,
    charge          varchar(255) null,
    version         varchar(255) null,
    step_id         bigint       null,
    created_at      datetime(6)  null,
    modified_at     datetime(6)  null,
    constraint FKpqqh67tayy819tarmo4fb9fn2
        foreign key (step_id) references kbook.step (step_id)
);

