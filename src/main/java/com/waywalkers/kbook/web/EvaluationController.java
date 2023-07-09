package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.EvaluationDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping(path = Path.API_EVALUATION)
    @ApiOperation(
            value = "평가 조회"
    )
    public ResultDto<EvaluationDto.EvaluationDetail> getEvaluationDetail(@PathVariable("evaluation-id") long evaluationId){
        return evaluationService.getEvaluationDetail(evaluationId);
    }

    @PostMapping(path = Path.API_EVALUATIONS)
    @ApiOperation(
            value = "평가 생성"
    )
    public ResultDto<Long> createEvaluation(@Valid @RequestBody EvaluationDto.PostEvaluation postEvaluation){
        return evaluationService.upsertEvaluation(postEvaluation);
    }

    @PutMapping(path = Path.API_EVALUATION)
    @ApiOperation(
            value = "평가 수정, 전문가 평가 추가"
    )
    public ResultDto<Long> updateEvaluation(@PathVariable("evaluation-id") long evaluationId, @RequestBody EvaluationDto.PutEvaluation putEvaluation){
        return evaluationService.createExpertEvaluation(evaluationId, putEvaluation);
    }

    @DeleteMapping(path = Path.API_EVALUATION)
    @ApiOperation(
            value = "평가 삭제"
    )
    public ResultDto deleteEvaluation(@PathVariable("evaluation-id") long evaluationId){
        return evaluationService.deleteEvaluation(evaluationId);
    }

}
