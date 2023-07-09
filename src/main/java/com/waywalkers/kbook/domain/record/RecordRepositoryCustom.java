package com.waywalkers.kbook.domain.record;

import com.waywalkers.kbook.dto.RecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordRepositoryCustom {
    Page<Record> findEvaluationRecord(RecordDto.EvaluationRecordParam evaluationRecordParam, Pageable pageable);
}
