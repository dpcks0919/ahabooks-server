package com.waywalkers.kbook.domain.profileBookRelation;

import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileBookRelationRepositoryCustom {
    Page<ProfileBookRelation> findByQDSLSearchValues(long id, ProfileBookRelationDto.SearchParam param, Pageable pageable);
}
