package com.waywalkers.kbook.domain.bookContent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookContentRepository extends JpaRepository<BookContent, Long> {
    List<BookContent> findAllByBook_Id(long id);
    List<BookContent> findAllByBook_IdAndPageNumIn(long id, List<Integer> pageNums);

    void deleteAllByBook_Id(long id);
}
