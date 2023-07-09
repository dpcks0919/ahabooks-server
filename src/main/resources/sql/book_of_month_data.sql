DELETE FROM `book_of_month`;
insert into `book_of_month` (book_of_month_id, book_id, description, contents_link, date, created_at, modified_at)
values (1, 1, 'description1', 'link1', '2022-07-01', now(), now()),
       (2, 1, 'description2', 'link1', '2022-08-11', now(), now()),
       (3, 2, 'description3', 'link1', '2022-09-11', now(), now())
;