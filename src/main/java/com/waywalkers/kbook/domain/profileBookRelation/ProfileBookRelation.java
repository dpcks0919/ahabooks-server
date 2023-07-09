package com.waywalkers.kbook.domain.profileBookRelation;

import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name="profile_book_relation")
@Entity
public class ProfileBookRelation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_book_relation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ColumnDefault("false")
    private boolean isFinished;

    @Column(name = "last_read_page")
    @ColumnDefault("0")
    private int lastReadPage;

    @Column(name = "last_listen_page")
    @ColumnDefault("0")
    private int lastListenPage;

    @ColumnDefault("0")
    private int views;

    @ColumnDefault("1")
    private int round;

    @OneToMany( mappedBy = "profileBookRelation",
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Record> records = new ArrayList<>();

    @Builder
    public ProfileBookRelation(Profile profile, Book book){
        this.profile = profile;
        this.book = book;
        this.lastReadPage = 0;
        this.lastListenPage = 0;
        this.views = 0;
        this.round = 1;
    }

    public void update(ProfileBookRelationDto.PutProfileBookRelation putProfileBookRelation){
        if(putProfileBookRelation.getIsFinished() != null){
            this.isFinished = putProfileBookRelation.getIsFinished();
        }

        if(putProfileBookRelation.getLastListenPage() != null){
            this.lastListenPage = putProfileBookRelation.getLastListenPage();
        }

        if (putProfileBookRelation.getLastReadPage() != null) {
            this.lastReadPage = putProfileBookRelation.getLastReadPage();
        }

    }
}
