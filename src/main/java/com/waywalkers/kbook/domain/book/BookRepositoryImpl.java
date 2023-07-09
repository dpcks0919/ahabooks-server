package com.waywalkers.kbook.domain.book;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.waywalkers.kbook.constant.BookType;
import com.waywalkers.kbook.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {
    public BookRepositoryImpl(){
        super(Book.class);
    }

    @Override
    public Page<Book> findByQDSLSearchValues(BookDto.SearchParam param, Pageable pageable) {
        QBook book = QBook.book;
        BooleanBuilder andBooleanBuilder = new BooleanBuilder();

        if(param.getFree() != null){
            if(Boolean.TRUE.equals(param.getFree())){
                andBooleanBuilder.and(book.isFree.eq(true));
            }else{
                andBooleanBuilder.and(book.isFree.eq(false));
            }
        }

        if(param.getEvaluation() != null){
            if(Boolean.TRUE.equals(param.getEvaluation())){
                andBooleanBuilder.and(book.type.eq(BookType.EVALUATION));
            }else{
                andBooleanBuilder.and(book.type.eq(BookType.GENERAL));
            }
        }

        if(param.getStepId() != null){
            andBooleanBuilder.and(book.step.id.eq(param.getStepId()));
        }

        if(param.getStatus() != null){
            andBooleanBuilder.and(book.status.eq(param.getStatus()));
        }

        JPQLQuery<Book> query = from(book);
        query.where(andBooleanBuilder);
        query = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Book> result = query.fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
