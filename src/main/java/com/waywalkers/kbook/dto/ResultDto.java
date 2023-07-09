package com.waywalkers.kbook.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto<T>{
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private T data;
    private PageDto page;
}
