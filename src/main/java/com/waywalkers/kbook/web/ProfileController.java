package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.ProfileDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.AccountProfileRelationService;
import com.waywalkers.kbook.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final AccountProfileRelationService accountProfileRelationService;

    @GetMapping(path = Path.API_PROFILES)
    @ApiOperation(
            value = "계정 프로필 조회"
    )
    public ResultDto<List<ProfileDto.ListOfProfiles>> getListOfProfiles(@PathVariable("account-id") long id){
        return profileService.getListOfProfiles(id);
    }

    @GetMapping(path = Path.API_PROFILE)
    @ApiOperation(
            value = "프로필 상세 조회"
    )
    public ResultDto<ProfileDto.ProfileDetail> getProfileDetail(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId){
        return profileService.getProfileDetail(accountId, profileId);
    }

    @PostMapping(path = Path.API_PROFILES)
    @ApiOperation(
            value = "계정 프로필 생성"
    )
    public ResultDto<Long> createAccountProfileRelation(@PathVariable("account-id") long id, @RequestBody ProfileDto.PostProfile postProfile){
        return accountProfileRelationService.createAccountProfileRelation(id, postProfile);
    }

    @PostMapping(path = Path.API_PROFILE)
    @ApiOperation(
            value = "프로필 수정"
    )
    public ResultDto<Long> updateProfile(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId, @RequestBody
    ProfileDto.PutProfile putProfile){
        return profileService.updateProfile(accountId, profileId, putProfile);
    }

    @DeleteMapping(path = Path.API_PROFILE)
    @ApiOperation(
            value = "프로필 삭제"
    )
    public ResultDto deleteProfile(@PathVariable("account-id") long accountId, @PathVariable("profile-id") long profileId){
        return profileService.deleteProfile(accountId, profileId);
    }

}
