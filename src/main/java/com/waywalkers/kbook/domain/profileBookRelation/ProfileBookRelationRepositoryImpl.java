package com.waywalkers.kbook.domain.profileBookRelation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileBookRelationRepositoryImpl extends QuerydslRepositorySupport implements ProfileBookRelationRepositoryCustom {
    public ProfileBookRelationRepositoryImpl(){
        super(ProfileBookRelation.class);
    }

    @Override
    public Page<ProfileBookRelation> findByQDSLSearchValues(long id, ProfileBookRelationDto.SearchParam param, Pageable pageable) {
        QProfileBookRelation profileBookRelation = QProfileBookRelation.profileBookRelation;

        BooleanBuilder andBooleanBuilder = new BooleanBuilder();
        andBooleanBuilder.and(profileBookRelation.profile.id.eq(id));

        if(Boolean.TRUE.equals(param.getOngoing())){
            BooleanBuilder orBooleanBuilder = new BooleanBuilder();
            orBooleanBuilder.or(profileBookRelation.lastListenPage.gt(0));
            orBooleanBuilder.or(profileBookRelation.lastReadPage.gt(0));
            andBooleanBuilder.and(orBooleanBuilder);
        }

        if(param.getType() != null){
            andBooleanBuilder.and(profileBookRelation.book.type.eq(param.getType()));
        }

        JPQLQuery<ProfileBookRelation> query = from(profileBookRelation);
        query.where(andBooleanBuilder);
        query = getQuerydsl().applyPagination(pageable, query);
        QueryResults<ProfileBookRelation> result = query.fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
