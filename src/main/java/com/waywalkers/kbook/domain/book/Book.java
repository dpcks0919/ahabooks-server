package com.waywalkers.kbook.domain.book;

import com.querydsl.core.util.StringUtils;
import com.waywalkers.kbook.constant.BookField;
import com.waywalkers.kbook.constant.BookStatus;
import com.waywalkers.kbook.constant.BookType;
import com.waywalkers.kbook.constant.BookVersion;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.bookContent.BookContent;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.step.Step;
import com.waywalkers.kbook.dto.BookDto;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name="book")
@Entity
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String name;

    private String auth;

    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="step_id")
    private Step step;

    @Setter
    @Column(name="total_page")
    private int totalPage;

    @Column(name="cover_image_url")
    private String coverImageUrl;

    @Column(name="book_file_url")
    private String bookFileUrl;

    @Column(name="narration_file_url")
    private String narrationFileUrl;

    @OneToMany(mappedBy = "book",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BookContent> bookContentList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BookVersion version;

    @Enumerated(EnumType.STRING)
    private BookField field;

    @Enumerated(EnumType.STRING)
    private BookType type;

    private boolean isFree;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @OneToMany(mappedBy = "book",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProfileBookRelation> profileBookRelations = new ArrayList<>();

    @Builder
    public Book(String name, String auth, String description, Step step, int totalPage, String coverImageUrl, String bookFileUrl, String narrationFileUrl, String version, BookType type, boolean isFree, BookField field) {
        this.name = name;
        this.auth = auth;
        this.description = description;
        this.step = step;
        this.totalPage = totalPage;
        this.coverImageUrl = coverImageUrl;
        this.bookFileUrl = bookFileUrl;
        this.narrationFileUrl = narrationFileUrl;
        if(StringUtils.isNullOrEmpty(version)){
           this.version = BookVersion.ORIGINAL;
        }else{
            this.version = BookVersion.valueOf(version);
        }
        this.type = type;
        this.field = field;
        this.isFree = isFree;
        this.status = BookStatus.WAITING;
    }

    public void updateBook(BookDto.PutBook putBook){
        if(putBook.getName() != null){
            this.name = putBook.getName();
        }

        if(putBook.getAuth() != null){
            this.auth = putBook.getAuth();
        }

        if(putBook.getDescription() != null){
            this.description = putBook.getDescription();
        }

        if(putBook.getCoverImageUrl() != null){
            this.coverImageUrl = putBook.getCoverImageUrl();
        }

        if(putBook.getBookFileUrl() != null){
            if(! putBook.getBookFileUrl().equals(this.bookFileUrl)){
                this.bookFileUrl = putBook.getBookFileUrl();
                this.status = BookStatus.WAITING;
            }
        }

        if(putBook.getNarrationFileUrl() != null){
            if(! putBook.getNarrationFileUrl().equals(this.narrationFileUrl)){
                this.narrationFileUrl = putBook.getNarrationFileUrl();
                this.status = BookStatus.WAITING;
            }
        }

        if(putBook.getVersion() != null){
            this.version = putBook.getVersion();
        }

        if(putBook.getField() != null){
            this.field = putBook.getField();
        }

        if(putBook.getIsFree() != null){
            this.isFree = putBook.getIsFree();
        }

        if(putBook.getType() != null){
            this.type = putBook.getType();
        }
    }

    public int getViews(){
        return profileBookRelations.stream().map(ProfileBookRelation::getViews).reduce(0, Integer::sum);
    }

    public void createBookContents(){
        this.status = BookStatus.DONE;
    }

    public void deleteBookContents(){
        this.status = BookStatus.WAITING;
    }

    public void failToCreateBookContents(){
        this.status = BookStatus.ERROR;
    }

    public boolean checkFileUrlExist(){
        if(StringUtils.isNullOrEmpty(this.bookFileUrl) || StringUtils.isNullOrEmpty(this.narrationFileUrl)){
            return false;
        }else {
            return true;
        }
    }
}
