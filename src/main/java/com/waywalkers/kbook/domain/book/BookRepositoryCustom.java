package com.waywalkers.kbook.domain.book;

import com.waywalkers.kbook.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<Book> findByQDSLSearchValues(BookDto.SearchParam param, Pageable pageable);
}
