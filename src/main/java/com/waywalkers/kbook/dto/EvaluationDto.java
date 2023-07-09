package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class EvaluationDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class EvaluationDetail {
        @NotNull
        private Long recordId;
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
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PostEvaluation {
        @NotNull
        private Long recordId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PutEvaluation {
        private String expertEvaluation;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class AverageFieldOfEvaluation{
        private Double averageAbsoluteScore;
        private Double averageLettersPerMin;
        private Double averageWordsPerMin;
        private Double averageTotalTime;
    }
}
