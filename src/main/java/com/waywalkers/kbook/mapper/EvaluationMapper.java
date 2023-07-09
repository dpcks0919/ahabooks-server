package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.evaluation.Evaluation;
import com.waywalkers.kbook.dto.EvaluationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EvaluationMapper {

    @Mapping(target = "recordId", expression = "java(evaluation.getRecord().getId())")
    EvaluationDto.EvaluationDetail EvaluationToEvaluationDetail(Evaluation evaluation);
}
