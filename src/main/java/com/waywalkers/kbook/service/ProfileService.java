package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.evaluation.EvaluationRepository;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.domain.profile.ProfileRepository;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelationRepository;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.ProfileDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileService {
    private final AccountRepository accountRepository;
    private final EvaluationRepository evaluationRepository;
    private final RecordRepository recordRepository;
    private final ProfileRepository profileRepository;
    private final ProfileBookRelationRepository profileBookRelationRepository;
    private final AccountProfileRelationRepository accountProfileRelationRepository;
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfProfiles(long id) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        List<ProfileDto.ListOfProfiles> listOfProfiles = account.getAccountProfileRelations().stream().map( accountProfileRelation ->
                profileMapper.ProfileToListOfProfiles(accountProfileRelation.getProfile())
        ).collect(Collectors.toList());

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfProfiles)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getProfileDetail(long accountId, long profileId) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Profile profile = profileRepository.findById(profileId).orElseThrow(()->new EntityNotFoundException("profile"));
        ProfileDto.ProfileDetail profileDetail = profileMapper.ProfileToProfileDetail(profile);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(profileDetail)
                .build();
    }

    public Profile postProfile(ProfileDto.PostProfile postProfile){
        Profile profile = profileRepository.save(
                Profile.builder()
                        .name(postProfile.getName())
                        .birthDate(postProfile.getBirthDate())
                        .imageUrl(postProfile.getImageUrl())
                        .gender(postProfile.getGender())
                        .build()
        );
        return profile;
    }

    public ResultDto updateProfile(long accountId, long profileId, ProfileDto.PutProfile putProfile) {
        // TODO
        //  파일 수정되면 기존 파일 삭제
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Profile profile = profileRepository.findById(profileId).orElseThrow(()->new EntityNotFoundException("profile"));
        profile.update(putProfile);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(profile.getId())
                .build();
    }

    public ResultDto deleteProfile(long accountId, long profileId) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        Profile profile = profileRepository.findById(profileId).orElseThrow(()->new EntityNotFoundException("profile"));
        List<Long> profileIds = Arrays.asList(profile.getId());
        // evaluation 삭제
        evaluationRepository.deleteAllByRecord_ProfileBookRelation_Profile_IdIn(profileIds);
        // record 삭제
        recordRepository.deleteAllByProfileBookRelation_Profile_IdIn(profileIds);
        // profile_book_relation 삭제
        profileBookRelationRepository.deleteAllByProfile_IdIn(profileIds);
        // account_profile_relation 삭제
        accountProfileRelationRepository.deleteAllByProfile_IdIn(profileIds);
        // profile 삭제
        profileRepository.deleteAllByIdIn(profileIds);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
