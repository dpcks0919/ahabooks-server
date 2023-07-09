package com.waywalkers.kbook.domain.accountProfileRelation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountProfileRelationRepository extends JpaRepository<AccountProfileRelation, Long> {
    Optional<AccountProfileRelation> findByAccount_IdAndProfile_Id(long accountId, long profileId);
    void deleteAllByAccount_Id(long accountId);

    void deleteAllByProfile_IdIn(List<Long> profileIds);
}
