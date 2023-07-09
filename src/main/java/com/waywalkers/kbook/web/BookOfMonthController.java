package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.BookOfMonthDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.BookOfMonthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookOfMonthController {
    private final BookOfMonthService bookOfMonthService;

    @GetMapping(path = Path.API_BOOK_OF_MONTHS)
    @ApiOperation(
            value = "이달의책 목록 조회"
    )
    public ResultDto<List<BookOfMonthDto.ListOfBookOfMonths>> getListOfBookOfMonths(Pageable pageable){
        return bookOfMonthService.getListOfBookOfMonths(pageable);
    }

    @GetMapping(path = Path.API_BOOK_OF_MONTH)
    @ApiOperation(
            value = "이달의책 상세 조회"
    )
    public ResultDto<BookOfMonthDto.BookOfMonthDetail> getBookOfMonthDetail(@PathVariable("month-id") Long id){
        return bookOfMonthService.getBookOfMonthDetail(id);
    }
}
