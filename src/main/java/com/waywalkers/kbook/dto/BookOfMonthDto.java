package com.waywalkers.kbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookOfMonthDto {
    @Data
    public static class ListOfBookOfMonths{
        private long id;
        private Integer year;
        private Integer month;
    }

    @Data
    public static class BookOfMonthDetail{
        private long id;
        private String description;
        private String contentsLink;
        private Integer year;
        private Integer month;
    }
}
