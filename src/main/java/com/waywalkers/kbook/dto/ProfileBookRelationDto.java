package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.BookType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ProfileBookRelationDto {
    @Data
    public static class SearchParam {
        private Boolean ongoing;
        private BookType type;
    }

    @Data
    public static class PutProfileBookRelation {
        private Boolean isFinished;
        private Integer lastReadPage;
        private Integer lastListenPage;
    }

}
