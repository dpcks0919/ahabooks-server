package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.step.Step;
import com.waywalkers.kbook.dto.StepDto;
import org.mapstruct.Mapper;

@Mapper
public interface StepMapper {
    StepDto.ListOfSteps StepToListOfSteps(Step step);
}
