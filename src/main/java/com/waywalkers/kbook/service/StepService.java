package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.step.Step;
import com.waywalkers.kbook.domain.step.StepRepository;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.dto.StepDto;
import com.waywalkers.kbook.mapper.StepMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class StepService {

    private final StepRepository stepRepository;
    private final StepMapper stepMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfSteps() {
        List<StepDto.ListOfSteps> listOfSteps = stepRepository.findAllByOrderById().stream().map(stepMapper::StepToListOfSteps).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfSteps)
                .build();
    }

}
