package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.book.BookRepository;
import com.waywalkers.kbook.domain.bookContent.BookContent;
import com.waywalkers.kbook.domain.bookContent.BookContentRepository;
import com.waywalkers.kbook.domain.bookOfMonth.BookOfMonthRepository;
import com.waywalkers.kbook.domain.evaluation.EvaluationRepository;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelationRepository;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.domain.step.Step;
import com.waywalkers.kbook.domain.step.StepRepository;
import com.waywalkers.kbook.dto.BookDto;
import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.mapper.BookMapper;
import com.waywalkers.kbook.mapper.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final AccountProfileRelationRepository accountProfileRelationRepository;
    private final BookRepository bookRepository;
    private final BookContentRepository bookContentRepository;
    private final ProfileBookRelationRepository profileBookRelationRepository;
    private final BookOfMonthRepository bookOfMonthRepository;
    private final RecordRepository recordRepository;
    private final EvaluationRepository evaluationRepository;
    private final StepRepository stepRepository;
    private final BookMapper bookMapper;
    private final PageMapper pageMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfBooksByParam(BookDto.SearchParam param, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByQDSLSearchValues(param, pageable);
        List<BookDto.ListOfBooks> listOfBooks = booksPage.getContent().stream().map(bookMapper::BookToListOfBooks).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfBooks)
                .page(pageMapper.PageToPageableDto(booksPage))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getBookDetail(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("book"));
        BookDto.BookDetail bookDetail = bookMapper.BookToBookDetail(book);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(bookDetail)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getProfileBooksByParam(long accountId, long profileId, ProfileBookRelationDto.SearchParam param, Pageable pageable) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Page<ProfileBookRelation> profileBookRelationsPage = profileBookRelationRepository.findByQDSLSearchValues(profileId, param, pageable);
        List<BookDto.ProfileBook> profileBooks = profileBookRelationsPage.getContent().stream().map(bookMapper::ProfileBookRelationToProfileBook).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(profileBooks)
                .page(pageMapper.PageToPageableDto(profileBookRelationsPage))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getBookContents(long bookId, BookDto.BookContentSearchParam param) {
        bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("book"));
        List<BookDto.BookContent> bookContents;
        if(param.getPages().isEmpty()){
            bookContents = bookContentRepository.findAllByBook_Id(bookId).stream()
                    .sorted(Comparator.comparingInt(BookContent::getWordIndex))
                    .map(bookMapper::BookContentToBookContent)
                    .collect(Collectors.toList());
        }else{
            bookContents = bookContentRepository.findAllByBook_IdAndPageNumIn(bookId, param.getPages()).stream()
                    .sorted(Comparator.comparingInt(BookContent::getWordIndex))
                    .map(bookMapper::BookContentToBookContent)
                    .collect(Collectors.toList());
        }

        return ResultDto.builder()
                .data(bookContents)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResultDto createBook(BookDto.PostBook postBook) {
        Step step = stepRepository.findById(postBook.getStepId()).orElseThrow(()-> new EntityNotFoundException("step"));
        Book book = bookRepository.save(Book.builder()
                .name(postBook.getName())
                .auth(postBook.getAuth())
                .description(postBook.getDescription())
                .version(postBook.getVersion())
                .coverImageUrl(postBook.getCoverImageUrl())
                .bookFileUrl(postBook.getBookFileUrl())
                .narrationFileUrl(postBook.getNarrationFileUrl())
                .field(postBook.getField())
                .isFree(postBook.isFree())
                .step(step)
                .type(postBook.getType())
                .build());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(book.getId())
                .build();
    }

    public ResultDto updateBook(Long id, BookDto.PutBook putBook) {
        // TODO
        //  파일 수정되면 기존 파일 삭제
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("book"));
        book.updateBook(putBook);
        if(putBook.getStepId() != null){
            Step step = stepRepository.findById(putBook.getStepId()).orElseThrow(()-> new EntityNotFoundException("step"));
            book.setStep(step);
        }

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(book.getId())
                .build();
    }

    public ResultDto deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("book"));
        bookOfMonthRepository.deleteAllByBook_Id(book.getId());
        evaluationRepository.deleteAllByRecord_ProfileBookRelation_Book_Id(book.getId());
        recordRepository.deleteAllByProfileBookRelation_Book_Id(book.getId());
        profileBookRelationRepository.deleteAllByBook_Id(book.getId());
        bookContentRepository.deleteAllByBook_Id(book.getId());
        bookRepository.delete(book);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResultDto upsertBookContents(long id, List<BookDto.BookContent> bookContentsDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("book"));
        if(! book.getBookContentList().isEmpty()){
            bookContentRepository.deleteAllByBook_Id(book.getId());
        }

        List<BookContent> bookContents = bookContentsDto.stream()
                .map(bookContentDto -> {
                    BookContent bookContent = bookMapper.BookContentToBookContent(bookContentDto);
                    bookContent.setBook(book);
                    return bookContent;
                })
                .collect(Collectors.toList());
        bookContentRepository.saveAll(bookContents);
        book.createBookContents();
        return ResultDto.builder()
                .data(book)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResultDto deleteBookContents(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("book"));
        bookContentRepository.deleteAllByBook_Id(book.getId());
        book.deleteBookContents();
        return ResultDto.builder()
                .data(book)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
