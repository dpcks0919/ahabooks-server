package com.waywalkers.kbook.domain.record;

import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.evaluation.Evaluation;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.BookDto;
import com.waywalkers.kbook.dto.ProfileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="record")
@Entity
public class Record extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    private int round;

    private boolean evaluated;

    private int page;

    @Column(name="record_file_url")
    private String recordFileUrl;

    private int time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_book_relation_id", nullable = false)
    private ProfileBookRelation profileBookRelation;

    @OneToOne(mappedBy = "record",
            fetch = FetchType.LAZY)
    private Evaluation evaluation;

    @Builder
    public Record(int round, int page, String recordFileUrl, int time, ProfileBookRelation profileBookRelation){
        this.round = round;
        this.evaluated = false;
        this.page = page;
        this.recordFileUrl = recordFileUrl;
        this.time = time;
        this.profileBookRelation = profileBookRelation;
    }

    public ProfileDto.EvaluateProfile getEvaluateProfile(){
        Profile profile = profileBookRelation.getProfile();
        return ProfileDto.EvaluateProfile.builder()
                .id(profile.getId())
                .name(profile.getName())
                .build();
    }

    public AccountDto.EvaluateAccount getEvaluateAccount(){
        Profile profile = profileBookRelation.getProfile();
        Account account = profile.getAccount();
        return AccountDto.EvaluateAccount.builder()
                .id(account.getId())
                .name(account.getName())
                .build();
    }

    public long getBookId(){
        return this.profileBookRelation.getBook().getId();
    }

    public String getBookName(){
        return this.profileBookRelation.getBook().getName();
    }
    public String getBookCoverImgUrl(){
        return this.profileBookRelation.getBook().getCoverImageUrl();
    }

    public Double getEvaluationAbsoluteScore(){
        return this.evaluation == null ? null : this.evaluation.getAbsoluteScore();
    }

    public Double getEvaluationLettersPerMin(){
        return this.evaluation == null ? null : this.evaluation.getLettersPerMin();
    }

    public Integer getEvaluationTotalLetters(){
        return this.evaluation == null ? null : this.evaluation.getTotalLetters();
    }

    public Double getEvaluationTotalTime(){
        return this.evaluation == null ? null : this.evaluation.getTotalTime();
    }

    public Integer getEvaluationTotalWords(){
        return this.evaluation == null ? null : this.evaluation.getTotalWords();
    }

    public Double getEvaluationWordsPerMin(){
        return this.evaluation == null ? null : this.evaluation.getWordsPerMin();
    }

    public String getEvaluationExpertEvaluation(){
        return this.evaluation == null ? null : this.evaluation.getExpertEvaluation();
    }

    public BookDto.RecordBook getRecordBook(){
        Book book = profileBookRelation.getBook();
        return BookDto.RecordBook.builder()
                .id(book.getId())
                .name(book.getName())
                .auth(book.getAuth())
                .description(book.getDescription())
                .coverImageUrl(book.getCoverImageUrl())
                .version(book.getVersion())
                .build();
    }

    public void evaluate(){
        this.evaluated = true;
    }

    public Long getEvaluationId(){
        return this.evaluation == null ? null : this.evaluation.getId();
    }
}
