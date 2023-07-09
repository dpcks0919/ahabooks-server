package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProfileMapper {

    ProfileDto.ListOfProfiles ProfileToListOfProfiles(Profile profile);

    @Mapping(target = "accountId", expression = "java(profile.getAccount().getId())")
    ProfileDto.ProfileDetail ProfileToProfileDetail(Profile profile);

    ProfileDto.AccountProfile ProfileToAccountProfile(Profile profile);
}
