package com.waywalkers.kbook.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class HomeDto {

    @Data
    public static class HomeCard{
        private Integer totalUser;
        private long totalPayment;
        private Integer totalRecord;
    }
}
