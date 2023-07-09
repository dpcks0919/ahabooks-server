package com.waywalkers.kbook.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Evaluation findByRecord_Id(long recordId);
    void deleteAllByRecord_ProfileBookRelation_Book_Id(long bookId);

    void deleteAllByRecord_ProfileBookRelation_Profile_IdIn(List<Long> account);

    void deleteByRecord_Id(long id);

    List<Evaluation> findAllByRecord_ProfileBookRelation_Book_Id(long bookId);
}
