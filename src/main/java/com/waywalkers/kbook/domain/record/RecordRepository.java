package com.waywalkers.kbook.domain.record;

import com.waywalkers.kbook.constant.BookType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {

    Page<Record> findAllByProfileBookRelation_Profile_IdAndProfileBookRelation_Book_Type(long id, BookType bookType, Pageable pageable);
    Page<Record> findAllByProfileBookRelation_Profile_Id(long id, Pageable pageable);
    List<Record> findAllByProfileBookRelation_Book_Id(long id);

    void deleteAllByProfileBookRelation_Book_Id(long bookId);

    void deleteAllByProfileBookRelation_Profile_IdIn(List<Long> profileIds);

    Optional<Record> findByIdAndProfileBookRelation_Id(long recordId, long profileBookRelationId);

    Page<Record> findAllByProfileBookRelation_Profile_IdAndProfileBookRelation_Book_Id(long profileId, long bookId, Pageable pageable);
}
