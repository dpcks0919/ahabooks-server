package com.waywalkers.kbook.schedule;

import com.waywalkers.kbook.constant.BookStatus;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.book.BookRepository;
import com.waywalkers.kbook.dto.BookDto;
import com.waywalkers.kbook.service.BookService;
import com.waywalkers.kbook.component.PythonModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookContentsSchedule {
    private final PythonModule pythonModule;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Transactional
    @Scheduled(cron = "${batch.book-content.cron}", zone = "Asia/Seoul")
    public void createBookContentsJob() {
        List<Book> books = bookRepository.findAllByStatusOrderByCreatedAtAsc(BookStatus.WAITING);
        if( ! books.isEmpty()){
            Book book = books.get(0);
            if(book.checkFileUrlExist()){
                try{
                    List<BookDto.BookContent> bookContents = pythonModule.makeBookContents(book.getId());
                    bookService.upsertBookContents(book.getId(), bookContents);
                }catch (Exception e){
                    book.failToCreateBookContents();
                    log.error(e.getMessage());
                }
            }else{
                book.failToCreateBookContents();
            }
        }else{
            log.info("Every book has its contents");
        }
    }
}
