drop table if exists record;
create table record
(
    record_id                bigint       not null
        primary key auto_increment,
    profile_book_relation_id bigint       not null,
    round                    int          not null,
    time                     int          not null,
    evaluated                bit          not null,
    page                     int          not null,
    record_file_url          varchar(255) null,
    created_at               datetime(6)  null,
    modified_at              datetime(6)  null,
    constraint FK8n3ol17nxlwm4ydpyhjtktp9g
        foreign key (profile_book_relation_id) references kbook.profile_book_relation (profile_book_relation_id)
);

