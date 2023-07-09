package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.RecordDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.RecordService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecordController {
    private final RecordService recordService;

    @GetMapping(path = Path.API_PROFILE_RECORDS)
    @ApiOperation(
            value = "프로필별 녹음 목록 조회"
    )
    public ResultDto<List<RecordDto.ProfileRecord>> getProfileRecordsByProfile(
            @PathVariable("account-id") long accountId,
            @PathVariable("profile-id") long profileId,
            Pageable pageable
    ){
        return recordService.getProfileRecordsByProfile(accountId, profileId, pageable);
    }

    @GetMapping(path = Path.API_PROFILE_BOOK_RECORDS)
    @ApiOperation(
            value = "프로필별 동화책 녹음 목록 조회"
    )
    public ResultDto<RecordDto.ProfileBookRecords> getProfileBookRecords(
            @PathVariable("account-id") long accountId,
            @PathVariable("profile-id") long profileId,
            @PathVariable("book-id") long bookId,
            Pageable pageable
    ){
        return recordService.getProfileBookRecords(accountId, profileId, bookId, pageable);
    }

    @GetMapping(path = Path.API_EVALUATION_RECORDS)
    @ApiOperation(
            value = "평가녹음 목록 조회"
    )
    public ResultDto<List<RecordDto.ListOfEvaluationRecords>> getListOfEvaluationRecords(RecordDto.EvaluationRecordParam evaluationRecordParam, Pageable pageable){
        return recordService.getListOfEvaluationRecords(evaluationRecordParam, pageable);
    }

    @GetMapping(path = Path.API_PROFILE_EVALUATION_RECORDS)
    @ApiOperation(
            value = "프로필별 평가녹음 목록 조회"
    )
    public ResultDto<List<RecordDto.ProfileRecord>> getEvaluationProfileRecordsByProfile(
            @PathVariable("account-id") long accountId,
            @PathVariable("profile-id") long profileId,
            Pageable pageable
    ){
        return recordService.getEvaluationProfileRecordsByProfile(accountId, profileId, pageable);
    }

    // TODO
    //  녹음 저장
    //  일반 녹음은 페이지 별, 라운드 별 녹음이 따로 진행
    //  평가 녹음은 달에 한번만 가능(어떻게 판단할건지?)
    //  평가용 녹음은 모든 페이지 통으로 녹화가 되어야 하는데 그렇게 가능한지?
    @PostMapping(path = Path.API_PROFILE_BOOK_RECORDS)
    @ApiOperation(
            value = "녹음 생성"
    )
    public ResultDto<Long> createProfileRecord(
            @PathVariable("account-id") long accountId,
            @PathVariable("profile-id") long profileId,
            @PathVariable("book-id") long bookId,
            @RequestBody RecordDto.PostProfileRecord postProfileRecord
    ){
        return recordService.postProfileRecord(accountId, profileId, bookId, postProfileRecord);
    }

    @DeleteMapping(path = Path.API_PROFILE_BOOK_RECORD)
    @ApiOperation(
            value = "녹음 삭제"
    )
    public ResultDto deleteProfileRecord(
            @PathVariable("account-id") long accountId,
            @PathVariable("profile-id") long profileId,
            @PathVariable("book-id") long bookId,
            @PathVariable("record-id") long recordId
    ){
        return recordService.deleteProfileRecord(accountId, profileId, bookId, recordId);
    }

    // TODO
    //  프로필-동화책 페이지별 녹음 목록

    // TODO
    //  프로필-동화책 녹음 목록 다운로드
    //  round를 리스트로 받아서 multiple file download
}
