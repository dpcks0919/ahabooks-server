package com.waywalkers.kbook.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class StepDto {
    @Data
    public static class ListOfSteps{
        private Long id;
        private String name;
        private String description;
    }

    @Builder
    @Data
    public static class BookStep{
        private String name;
    }

}
