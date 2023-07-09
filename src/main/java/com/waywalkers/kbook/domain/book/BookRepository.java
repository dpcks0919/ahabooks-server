package com.waywalkers.kbook.domain.book;

import com.waywalkers.kbook.constant.BookStatus;
import com.waywalkers.kbook.constant.BookType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Page<Book> findAllByType(BookType bookType, Pageable pageable);
    Page<Book> findAllByStep_Id(Long id, Pageable pageable);

    List<Book> findAllByStatusOrderByCreatedAtAsc(BookStatus bookStatus);
}
