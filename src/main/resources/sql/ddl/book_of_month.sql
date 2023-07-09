drop table if exists book_of_month;
create table book_of_month
(
    book_of_month_id bigint       not null
        primary key auto_increment,
    date            date         null,
    book_id          bigint       null,
    description      varchar(255) null,
    contents_link    varchar(255) null,
    created_at       datetime(6)  null,
    modified_at      datetime(6)  null,
    constraint FK3ic90tacf9hewcf7djwecmh2y
        foreign key (book_id) references kbook.book (book_id)
);

