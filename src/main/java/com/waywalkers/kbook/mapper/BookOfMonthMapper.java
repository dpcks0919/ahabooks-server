package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.bookOfMonth.BookOfMonth;
import com.waywalkers.kbook.dto.BookOfMonthDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookOfMonthMapper {

    @Mapping(target = "year", expression = "java(bookOfMonth.getYear())")
    @Mapping(target = "month", expression = "java(bookOfMonth.getMonth())")
    BookOfMonthDto.ListOfBookOfMonths BookOfMonthToListOfBookOfMonths(BookOfMonth bookOfMonth);

    @Mapping(target = "year", expression = "java(bookOfMonth.getYear())")
    @Mapping(target = "month", expression = "java(bookOfMonth.getMonth())")
    BookOfMonthDto.BookOfMonthDetail BookOfMonthToBookOfMonthDetail(BookOfMonth bookOfMonth);

}
