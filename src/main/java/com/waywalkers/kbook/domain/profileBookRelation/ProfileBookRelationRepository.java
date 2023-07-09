package com.waywalkers.kbook.domain.profileBookRelation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileBookRelationRepository extends JpaRepository<ProfileBookRelation, Long>, ProfileBookRelationRepositoryCustom {
    Optional<ProfileBookRelation> findByProfile_IdAndBook_Id(long profileId, long BookId);
    void deleteAllByBook_Id(long BookId);
    void deleteByProfile_IdAndBook_Id(long profileId, long BookId);

    void deleteAllByProfile_IdIn(List<Long> profileIds);
}
