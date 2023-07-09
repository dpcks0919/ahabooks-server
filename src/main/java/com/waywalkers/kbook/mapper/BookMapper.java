package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.bookContent.BookContent;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface BookMapper {
    @Mapping(target = "step", expression = "java(book.getStep().getName())")
    @Mapping(target = "views", expression = "java(book.getViews())")
    @Mapping(target = "isFree", expression = "java(book.isFree())")
    BookDto.ListOfBooks BookToListOfBooks(Book book);

    @Mapping(target = "step", expression = "java(book.getStep().getName())")
    @Mapping(target = "isFree", expression = "java(book.isFree())")
    BookDto.BookDetail BookToBookDetail(Book book);

    @Mapping(target = "description", expression = "java(profileBookRelation.getBook().getDescription())")
    @Mapping(target = "auth", expression = "java(profileBookRelation.getBook().getAuth())")
    @Mapping(target = "coverImageUrl", expression = "java(profileBookRelation.getBook().getCoverImageUrl())")
    @Mapping(target = "name", expression = "java(profileBookRelation.getBook().getName())")
    @Mapping(target = "bookId", expression = "java(profileBookRelation.getBook().getId())")
    BookDto.ProfileBook ProfileBookRelationToProfileBook(ProfileBookRelation profileBookRelation);

    BookDto.BookContent BookContentToBookContent(BookContent bookContent);

    BookContent BookContentToBookContent(BookDto.BookContent bookContent);
}
