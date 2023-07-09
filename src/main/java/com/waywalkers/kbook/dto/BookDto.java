package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.BookField;
import com.waywalkers.kbook.constant.BookStatus;
import com.waywalkers.kbook.constant.BookType;
import com.waywalkers.kbook.constant.BookVersion;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookDto {
    @Data
    public static class SearchParam {
        private Boolean free;
        private Boolean evaluation;
        private Long stepId;
        private BookStatus status;
    }

    @Data
    public static class DownloadParam {
        private List<Integer> rounds;
    }



    @Data
    public static class ListOfBooks{
        private Long id;
        private String name;
        private String auth;
        private String description;
        private String coverImageUrl;
        private BookVersion version;
        private BookType type;
        private BookField field;
        private Boolean isFree;
        private Integer views;
        private String step;
        private BookStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Data
    public static class BookDetail{
        private Long id;
        private String name;
        private String auth;
        private String description;
        private String coverImageUrl;
        private String bookFileUrl;
        private String narrationFileUrl;
        private BookVersion version;
        private BookType type;
        private BookField field;
        private Boolean isFree;
        private String step;
        private BookStatus status;
        private LocalDateTime modifiedAt;
    }

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class PostBook{
        private String name;
        private String auth;
        private String description;
        private String version;
        @NotNull
        private Long stepId;
        @NotBlank
        private String coverImageUrl;
        @NotBlank
        private String bookFileUrl;
        @NotBlank
        private String narrationFileUrl;
        private boolean isFree;
        private BookField field;
        private BookType type;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PutBook{
        private String name;
        private String auth;
        private String description;
        private BookVersion version;
        private Long stepId;
        @NotBlank
        private String coverImageUrl;
        @NotBlank
        private String bookFileUrl;
        @NotBlank
        private String narrationFileUrl;
        private Boolean isFree;
        private BookField field;
        private BookType type;
    }

    @Builder
    @Data
    public static class RecordBook{
        private Long id;
        private String name;
        private String auth;
        private String description;
        private String coverImageUrl;
        private BookVersion version;
    }

    @Data
    public static class ProfileBook{
        private long id;
        private Long bookId;
        private String name;
        private String coverImageUrl;
        private String auth;
        private String description;
        private Integer lastReadPage;
        private Integer lastListenPage;
        private int round;
    }

    @Data
    public static class BookContentSearchParam {
        private List<Integer> pages = new ArrayList<>();
    }

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class BookContent{
        private Integer wordIndex;
        private String word;
        private double start_time;
        private double duration;
        private Integer pageNum;
    }
}
