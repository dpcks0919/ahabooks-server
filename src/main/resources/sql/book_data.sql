DELETE FROM `book`;
insert into `book`(book_id, name, auth, description, step_id, total_page, cover_image_url, epub_file_url, version, type, charge, created_at, modified_at)
values (1, 'name1', 'auth1', 'descripton1', 1, 10, 'image1', 'file1', 'EASY', 'EVALUATION', 'FREE', now(), now()),
       (2, 'name2', 'auth2', 'descripton2', 1, 10, 'image2', 'file2', 'ORIGINAL', 'EVALUATION', 'PAY', now(), now()),
       (3, 'name3', 'auth3', 'descripton3', 2, 20, 'image3', 'file3', 'ORIGINAL', 'GENERAL', 'FREE', now(), now()),
       (4, 'name4', 'auth4', 'descripton4', 3, 30, 'image4', 'file4', 'ORIGINAL', 'GENERAL', 'PAY', now(), now()),
       (5, 'name5', 'auth5', 'descripton5', 3, 30, 'image5', 'file5', 'EASY', 'GENERAL', 'PAY', now(), now())
;