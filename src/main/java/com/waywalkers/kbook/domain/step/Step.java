package com.waywalkers.kbook.domain.step;

import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.dto.StepDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name="step")
@Entity
public class Step extends BaseTimeEntity {
    @Id
    @Column(name = "step_id")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "step",
            fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Book> books = new ArrayList<>();

    public StepDto.BookStep getBookStep(){
        return StepDto.BookStep.builder()
                .name(this.name)
                .build();
    }
}
