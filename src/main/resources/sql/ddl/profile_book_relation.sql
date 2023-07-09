drop table if exists profile_book_relation;
create table profile_book_relation
(
    profile_book_relation_id bigint      not null
        primary key auto_increment,
    profile_id               bigint      null,
    book_id                  bigint      null,
    last_listen_page         int         null,
    last_read_page           int         null,
    listening                bit        not null,
    reading                  bit        not null,
    views                    int         not null,
    created_at               datetime(6) null,
    modified_at              datetime(6) null,
    constraint FKm4phc6kmwxbvtlv7ht8919q8b
        foreign key (book_id) references kbook.book (book_id),
    constraint FKsgiggyaafpu8nnitp42624l3
        foreign key (profile_id) references kbook.profile (profile_id)
);

