package com.waywalkers.kbook.domain.bookOfMonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOfMonthRepository extends JpaRepository<BookOfMonth, Long> {
    void deleteAllByBook_Id(long bookId);
}
