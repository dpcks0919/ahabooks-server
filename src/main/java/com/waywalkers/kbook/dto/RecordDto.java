package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RecordDto {

    @Data
    public static class PostProfileRecord {
        private Integer round;
        private Integer page;
        private Integer time;
        private String recordFileUrl;
    }

    @Data
    @Builder
    public static class ListOfEvaluationRecords {
        private Long id;
        private String bookName;
        private String bookCoverImageUrl;
        private Long evaluationId;
        private Double absoluteScore;
        private Double lettersPerMin;
        private Integer totalLetters;
        private Double totalTime;
        private Integer totalWords;
        private Double wordsPerMin;
        private String expertEvaluation;
        private Double averageAbsoluteScore;
        private Double averageLettersPerMin;
        private Double averageWordsPerMin;
        private Double averageTotalTime;
        private LocalDateTime createdAt;
        private ProfileDto.EvaluateProfile profile;
        private AccountDto.EvaluateAccount account;
    }

    @Data
    public static class ProfileRecord{
        private Long id;
        private BookDto.RecordBook book;
        private LocalDateTime createdAt;
        private Integer time;
        private String recordFileUrl;
    }

    @Builder
    @Data
    public static class ProfileBookRecords{
        private Long bookId;
        private String auth;
        private String description;
        private String coverImageUrl;
        private String bookName;
        List<ProfileBookRecord> profileBookRecords;
    }

    @Data
    public static class ProfileBookRecord{
        private Long id;
        private String recordFileUrl;
        private Integer round;
        private LocalDateTime createdAt;
        private Long evaluationId;
    }

    @Data
    public static class EvaluationRecordParam{
        private Boolean isEvaluated;
    }

}
