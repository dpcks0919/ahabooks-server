package com.waywalkers.kbook.domain.bookContent;

import com.waywalkers.kbook.domain.book.Book;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="book_content")
@Entity
public class BookContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_content_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "word_index")
    private int wordIndex;

    private String word;

    private double start_time;

    private double duration;

    @Column(name = "page_num")
    private int pageNum;

    @Builder
    public BookContent(Book book, int wordIndex, String word, double start_time, double duration, int pageNum){
        this.book = book;
        this.wordIndex = wordIndex;
        this.word = word;
        this.start_time = start_time;
        this.duration = duration;
        this.pageNum = pageNum;
    }
}
