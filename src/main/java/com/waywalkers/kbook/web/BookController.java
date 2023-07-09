package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.dto.BookDto;
import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.BookService;
import com.waywalkers.kbook.service.ProfileBookRelationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final ProfileBookRelationService profileBookRelationService;

    @GetMapping(path = Path.API_BOOKS)
    @ApiOperation(
            value = "동화책 목록 조회"
    )
    public ResultDto<List<BookDto.ListOfBooks>> getListOfBooksByParam(BookDto.SearchParam param, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable){
        return bookService.getListOfBooksByParam(param, pageable);
    }

    @GetMapping(path = Path.API_BOOK)
    @ApiOperation(
            value = "동화책 상세 조회"
    )
    public ResultDto<BookDto.BookDetail> getBookDetail(@PathVariable("book-id") Long id){
        return bookService.getBookDetail(id);
    }

    @PostMapping(path= Path.API_BOOKS)
    @ApiOperation(
            value = "동화책 생성"
    )
    public ResultDto<Long> createBook(@Valid @RequestBody BookDto.PostBook postBook){
        return bookService.createBook(postBook);
    }

    @PutMapping(path = Path.API_BOOK)
    @ApiOperation(
            value = "동화책 수정"
    )
    public ResultDto<Long> updateBook(@PathVariable("book-id") Long id, @Valid @RequestBody BookDto.PutBook putBook){
        return bookService.updateBook(id, putBook);
    }

    @DeleteMapping(path = Path.API_BOOK)
    @ApiOperation(
            value = "동화책 삭제"
    )
    public ResultDto deleteBook(@PathVariable("book-id") Long id){
        return bookService.deleteBook(id);
    }

    @PostMapping(path = Path.API_BOOK_CONTENT)
    @ApiOperation(
            value = "동화책 내용 생성"
    )
    public ResultDto<Book> createBookContents(@PathVariable("book-id") Long id, @RequestBody List<BookDto.BookContent> bookContents){
        return bookService.upsertBookContents(id, bookContents);
    }

    @PutMapping(path = Path.API_BOOK_CONTENT)
    @ApiOperation(
            value = "동화책 내용 수정"
    )
    public ResultDto<Book> updateBookContents(@PathVariable("book-id") Long id, @RequestBody List<BookDto.BookContent> bookContents){
        return bookService.upsertBookContents(id, bookContents);
    }

    @DeleteMapping(path = Path.API_BOOK_CONTENT)
    @ApiOperation(
            value = "동화책 내용 삭제"
    )
    public ResultDto<Book> createBookContent(@PathVariable("book-id") Long id){
        return bookService.deleteBookContents(id);
    }

    @GetMapping(path = Path.API_PROFILE_BOOKS)
    @ApiOperation(
            value = "담은 동화책 보기"
    )
    public ResultDto<List<BookDto.ProfileBook>> getProfileBooks(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId, ProfileBookRelationDto.SearchParam param, Pageable pageable){
        return bookService.getProfileBooksByParam(accountId, profileId, param, pageable);
    }

    @GetMapping(path = Path.API_BOOK_READ)
    @ApiOperation(
            value = "동화책 듣기 (책 내용 가져오기)"
    )
    public ResultDto<List<BookDto.BookContent>> readBook(@PathVariable("book-id") long bookId, BookDto.BookContentSearchParam param){
        // TODO
        //  profile-id 받아와서 마지막 읽은 페이지 업데이트
        //  현재 몇 회차까지 읽었는지 데이터를 같이 보내면 회차 저장 가능
        return bookService.getBookContents(bookId, param);
    }

    // TODO
    //  동화책 조회수는 어떻게 측정할지

    @PostMapping(path = Path.API_PROFILE_BOOK)
    @ApiOperation(
            value = "동화책 담기"
    )
    public ResultDto<Long> createProfileBook(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId, @PathVariable("book-id") long bookId){
        return profileBookRelationService.createProfileBookRelation(accountId, profileId, bookId);
    }

    @PutMapping(path = Path.API_PROFILE_BOOK)
    @ApiOperation(
            value = "담은 동화책 수정"
    )
    public ResultDto<Long> updateProfileBook(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId, @PathVariable("book-id") long bookId, @RequestBody ProfileBookRelationDto.PutProfileBookRelation putProfileBookRelation){
        return profileBookRelationService.putProfileBookRelation(accountId, profileId, bookId, putProfileBookRelation);
    }

    @DeleteMapping(path = Path.API_PROFILE_BOOK)
    @ApiOperation(
            value = "동화책 담기 취소"
    )
    public ResultDto deleteProfileBook(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId, @PathVariable("book-id") long bookId){
        return profileBookRelationService.deleteProfileBookRelation(accountId, profileId, bookId);
    }
}
