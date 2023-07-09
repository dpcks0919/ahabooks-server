package com.waywalkers.kbook.handler;

import com.waywalkers.kbook.constant.Constants;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.exception.EvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EvaluationException.class)
    public ResultDto EvaluationExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage("evaluation creation error");
        resultDto.setErrorCode(Constants.ErrorCode.EvaluationCreationException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(ServletException.class)
    public ResultDto ServletExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage("filter error");
        resultDto.setErrorCode(Constants.ErrorCode.ServletException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResultDto FileNotFoundExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage("file doesn't exist");
        resultDto.setErrorCode(Constants.ErrorCode.FileNotFoundException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(IOException.class)
    public ResultDto IOExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage("io exception");
        resultDto.setErrorCode(Constants.ErrorCode.IOExceptionException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResultDto entityExistsExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage(e.getMessage() + " already exists");
        resultDto.setErrorCode(Constants.ErrorCode.EntityExistsException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResultDto entityNotFoundExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage(e.getMessage() + " not found");
        resultDto.setErrorCode(Constants.ErrorCode.EntityNotFoundException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDto ValidatioExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage(e.getMessage());
        resultDto.setErrorCode(Constants.ErrorCode.ValidationException);
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }

    @ExceptionHandler(Exception.class)
    public ResultDto globalExceptionHandler(Exception e){
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage(e.getMessage());
        resultDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return resultDto;
    }
}
