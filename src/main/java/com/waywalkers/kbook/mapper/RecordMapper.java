package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.dto.RecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RecordMapper {
    @Mapping(target = "bookName", expression = "java(record.getBookName())")
    @Mapping(target = "account", expression = "java(record.getEvaluateAccount())")
    @Mapping(target = "profile", expression = "java(record.getEvaluateProfile())")
    @Mapping(target = "bookCoverImageUrl", expression = "java(record.getBookCoverImgUrl())")
    @Mapping(target = "evaluationId", expression = "java(record.getEvaluationId())")
    @Mapping(target = "absoluteScore", expression = "java(record.getEvaluationAbsoluteScore())")
    @Mapping(target = "lettersPerMin", expression = "java(record.getEvaluationLettersPerMin())")
    @Mapping(target = "totalLetters", expression = "java(record.getEvaluationTotalLetters())")
    @Mapping(target = "totalTime", expression = "java(record.getEvaluationTotalTime())")
    @Mapping(target = "totalWords", expression = "java(record.getEvaluationTotalWords())")
    @Mapping(target = "wordsPerMin", expression = "java(record.getEvaluationWordsPerMin())")
    @Mapping(target = "expertEvaluation", expression = "java(record.getEvaluationExpertEvaluation())")
    RecordDto.ListOfEvaluationRecords RecordToListOfEvaluationRecords(Record record);

    @Mapping(target = "book", expression = "java(record.getRecordBook())")
    RecordDto.ProfileRecord RecordToProfileRecordDto(Record record);

    @Mapping(target = "evaluationId", expression = "java(record.getEvaluationId())")
    RecordDto.ProfileBookRecord RecordToProfileBookRecordDto(Record record);
}
