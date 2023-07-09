package com.waywalkers.kbook.service;

import com.waywalkers.kbook.component.PythonModule;
import com.waywalkers.kbook.domain.evaluation.Evaluation;
import com.waywalkers.kbook.domain.evaluation.EvaluationRepository;
import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.EvaluationDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.exception.EvaluationException;
import com.waywalkers.kbook.mapper.EvaluationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class EvaluationService {
    private final RecordRepository recordRepository;
    private final EvaluationRepository evaluationRepository;
    private final PythonModule pythonModule;
    private final EvaluationMapper evaluationMapper;

    @Transactional(readOnly = true)
    public ResultDto getEvaluationDetail(long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new EntityNotFoundException("evaluation"));
        EvaluationDto.EvaluationDetail evaluationDetail = evaluationMapper.EvaluationToEvaluationDetail(evaluation);
        EvaluationDto.AverageFieldOfEvaluation averageFieldOfEvaluation = this.calculateAverage(evaluation.getRecord().getBookId());
        evaluationDetail.setAverageWordsPerMin(averageFieldOfEvaluation.getAverageWordsPerMin());
        evaluationDetail.setAverageLettersPerMin(averageFieldOfEvaluation.getAverageLettersPerMin());
        evaluationDetail.setAverageAbsoluteScore(averageFieldOfEvaluation.getAverageAbsoluteScore());
        evaluationDetail.setAverageTotalTime(averageFieldOfEvaluation.getAverageTotalTime());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(evaluationDetail)
                .build();
    }

    public ResultDto upsertEvaluation(EvaluationDto.PostEvaluation postEvaluation) {
        Record record = recordRepository.findById(postEvaluation.getRecordId()).orElseThrow(()-> new EntityNotFoundException("record"));
        Evaluation evaluation = this.makeEvaluation(record);
        record.evaluate();

        Evaluation existEvalution = evaluationRepository.findByRecord_Id(record.getId());
        if(existEvalution == null){
            evaluationRepository.save(evaluation);
        }else{
            existEvalution.update(evaluation);
        }

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(evaluation.getId())
                .build();
    }

    private Evaluation makeEvaluation(Record record){
        Evaluation evaluation;
        try{
            evaluation = pythonModule.makeEvaluation(record.getId());
            evaluation.setRecord(record);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new EvaluationException();
        }
        return evaluation;
    }

    public ResultDto createExpertEvaluation(long evaluationId, EvaluationDto.PutEvaluation putEvaluation) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(()-> new EntityNotFoundException("evaluation"));
        evaluation.createExpertEvaluation(putEvaluation);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(evaluation.getId())
                .build();
    }

    public ResultDto deleteEvaluation(long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public EvaluationDto.AverageFieldOfEvaluation calculateAverage(long bookId){
        List<Evaluation> evaluations = evaluationRepository.findAllByRecord_ProfileBookRelation_Book_Id(bookId);
        EvaluationDto.AverageFieldOfEvaluation averageFieldOfEvaluation = EvaluationDto.AverageFieldOfEvaluation.builder()
                .averageAbsoluteScore(evaluations.stream().mapToDouble(Evaluation::getAbsoluteScore).average().orElse(0))
                .averageLettersPerMin(evaluations.stream().mapToDouble(Evaluation::getLettersPerMin).average().orElse(0))
                .averageWordsPerMin(evaluations.stream().mapToDouble(Evaluation::getWordsPerMin).average().orElse(0))
                .averageTotalTime(evaluations.stream().mapToDouble(Evaluation::getTotalTime).average().orElse(0))
                .build();
        return averageFieldOfEvaluation;
    }
}
