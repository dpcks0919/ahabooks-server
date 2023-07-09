package com.waywalkers.kbook.service;

import com.waywalkers.kbook.constant.BookType;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.book.BookRepository;
import com.waywalkers.kbook.domain.evaluation.EvaluationRepository;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelationRepository;
import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.EvaluationDto;
import com.waywalkers.kbook.dto.RecordDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.mapper.PageMapper;
import com.waywalkers.kbook.mapper.RecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RecordService {
    private final AccountProfileRelationRepository accountProfileRelationRepository;
    private final BookRepository bookRepository;
    private final EvaluationRepository evaluationRepository;
    private final RecordRepository recordRepository;
    private final ProfileBookRelationRepository profileBookRelationRepository;

    private final EvaluationService evaluationService;

    private final RecordMapper recordMapper;
    private final PageMapper pageMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfEvaluationRecords(RecordDto.EvaluationRecordParam evaluationRecordParam, Pageable pageable) {
        Page<Record> recordsPage = recordRepository.findEvaluationRecord(evaluationRecordParam, pageable);
        List<RecordDto.ListOfEvaluationRecords> evaluationRequestList = recordsPage.getContent().stream()
                .map( record -> {
                    RecordDto.ListOfEvaluationRecords listOfEvaluationRecords = recordMapper.RecordToListOfEvaluationRecords(record);
                    if(record.getEvaluation() != null){
                        EvaluationDto.AverageFieldOfEvaluation averageFieldOfEvaluation = evaluationService.calculateAverage(record.getBookId());
                        listOfEvaluationRecords.setAverageWordsPerMin(averageFieldOfEvaluation.getAverageWordsPerMin());
                        listOfEvaluationRecords.setAverageLettersPerMin(averageFieldOfEvaluation.getAverageLettersPerMin());
                        listOfEvaluationRecords.setAverageAbsoluteScore(averageFieldOfEvaluation.getAverageAbsoluteScore());
                        listOfEvaluationRecords.setAverageTotalTime(averageFieldOfEvaluation.getAverageTotalTime());
                    }
                     return listOfEvaluationRecords;
                    })
                .collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(evaluationRequestList)
                .page(pageMapper.PageToPageableDto(recordsPage))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getProfileRecordsByProfile(long accountId, long profileId, Pageable pageable) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Page<Record> recordsPage = recordRepository.findAllByProfileBookRelation_Profile_Id(profileId, pageable);
        List<RecordDto.ProfileRecord> profileRecords = recordsPage.getContent().stream().map(recordMapper::RecordToProfileRecordDto).collect(Collectors.toList());
        return ResultDto.builder()
                .data(profileRecords)
                .page(pageMapper.PageToPageableDto(recordsPage))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getEvaluationProfileRecordsByProfile(long accountId, long profileId, Pageable pageable) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Page<Record> recordsPage = recordRepository.findAllByProfileBookRelation_Profile_IdAndProfileBookRelation_Book_Type(profileId, BookType.EVALUATION, pageable);
        List<RecordDto.ProfileRecord> profileRecords = recordsPage.getContent().stream().map(recordMapper::RecordToProfileRecordDto).collect(Collectors.toList());
        return ResultDto.builder()
                .data(profileRecords)
                .page(pageMapper.PageToPageableDto(recordsPage))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getProfileBookRecords(long accountId, long profileId, long bookId, Pageable pageable) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("book"));
        Page<Record> recordsPage = recordRepository.findAllByProfileBookRelation_Profile_IdAndProfileBookRelation_Book_Id(profileId, bookId, pageable);
        RecordDto.ProfileBookRecords profileBookRecords = RecordDto.ProfileBookRecords.builder()
                .bookId(book.getId())
                .auth(book.getAuth())
                .description(book.getDescription())
                .coverImageUrl(book.getCoverImageUrl())
                .bookName(book.getName())
                .profileBookRecords(recordsPage.getContent().stream().map(recordMapper::RecordToProfileBookRecordDto).collect(Collectors.toList()))
                .build();

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .page(pageMapper.PageToPageableDto(recordsPage))
                .data(profileBookRecords)
                .build();
    }

    public ResultDto postProfileRecord(long accountId, long profileId, long bookId, RecordDto.PostProfileRecord postProfileRecord) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        ProfileBookRelation profileBookRelation = profileBookRelationRepository.findByProfile_IdAndBook_Id(profileId, bookId).orElseThrow(() -> new EntityNotFoundException("profileBookRelation"));
        Record record = Record.builder()
                .page(postProfileRecord.getPage())
                .round(postProfileRecord.getRound())
                .recordFileUrl(postProfileRecord.getRecordFileUrl())
                .time(postProfileRecord.getTime())
                .profileBookRelation(profileBookRelation)
                .build();
        recordRepository.save(record);
        return ResultDto.builder()
                .data(record.getId())
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResultDto deleteProfileRecord(long accountId, long profileId, long bookId, long recordId) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        ProfileBookRelation profileBookRelation = profileBookRelationRepository.findByProfile_IdAndBook_Id(profileId, bookId).orElseThrow(() -> new EntityNotFoundException("profileBookRelation"));
        Record record = recordRepository.findByIdAndProfileBookRelation_Id(recordId, profileBookRelation.getId()).orElseThrow(() -> new EntityNotFoundException("record"));
        // evaluation 제거
        evaluationRepository.deleteByRecord_Id(record.getId());
        // record 제거
        recordRepository.delete(record);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
