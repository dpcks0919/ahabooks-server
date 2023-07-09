package com.waywalkers.kbook.domain.record;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.waywalkers.kbook.constant.BookType;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.book.QBook;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.profileBookRelation.QProfileBookRelation;
import com.waywalkers.kbook.dto.RecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
public class RecordRepositoryImpl extends QuerydslRepositorySupport implements RecordRepositoryCustom{
    public RecordRepositoryImpl(){
        super(Record.class);
    }

    @Override
    public Page<Record> findEvaluationRecord(RecordDto.EvaluationRecordParam evaluationRecordParam, Pageable pageable) {
        QRecord record = QRecord.record;

        BooleanBuilder andBooleanBuilder = new BooleanBuilder();
        andBooleanBuilder.and(record.profileBookRelation.book.type.eq(BookType.EVALUATION));
        if(Boolean.TRUE.equals(evaluationRecordParam.getIsEvaluated())){
            andBooleanBuilder.and(record.evaluated.eq(true));
        }else if(Boolean.FALSE.equals(evaluationRecordParam.getIsEvaluated())){
            andBooleanBuilder.and(record.evaluated.eq(false));
        }

        JPQLQuery<Record> query = from(record);
        query.where(andBooleanBuilder);
        query = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Record> result = query.fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
