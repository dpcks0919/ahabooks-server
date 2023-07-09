package com.waywalkers.kbook.domain.bookOfMonth;

import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table(name="book_of_month")
@Entity
public class BookOfMonth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_of_month_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    private String description;

    @Column(name = "contents_link")
    private String contentsLink;

    private LocalDate date;

    public int getYear(){
        return date.getYear();
    }

    public int getMonth(){
        return date.getMonthValue();
    }
}
