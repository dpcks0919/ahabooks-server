package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.bookOfMonth.BookOfMonth;
import com.waywalkers.kbook.domain.bookOfMonth.BookOfMonthRepository;
import com.waywalkers.kbook.dto.BookOfMonthDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.mapper.BookOfMonthMapper;
import com.waywalkers.kbook.mapper.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BookOfMonthService {
    private final BookOfMonthRepository bookOfMonthRepository;
    private final BookOfMonthMapper bookOfMonthMapper;
    private final PageMapper pageMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfBookOfMonths(Pageable pageable) {
        Page<BookOfMonth> bookOfMonthPage = bookOfMonthRepository.findAll(pageable);
        List<BookOfMonthDto.ListOfBookOfMonths> listOfBookOfMonths = bookOfMonthPage.getContent().stream().map(bookOfMonthMapper::BookOfMonthToListOfBookOfMonths).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfBookOfMonths)
                .page(pageMapper.PageToPageableDto(bookOfMonthPage))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getBookOfMonthDetail(Long id) {
        BookOfMonth bookOfMonth = bookOfMonthRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("bookOfMonth"));
        BookOfMonthDto.BookOfMonthDetail bookOfMonthDetail = bookOfMonthMapper.BookOfMonthToBookOfMonthDetail(bookOfMonth);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(bookOfMonthDetail)
                .build();
    }
}
