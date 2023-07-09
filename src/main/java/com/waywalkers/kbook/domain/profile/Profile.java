package com.waywalkers.kbook.domain.profile;

import com.waywalkers.kbook.constant.Gender;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelation;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.step.Step;
import com.waywalkers.kbook.dto.ProfileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name="profile")
@Entity
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recommendation_step_id")
    private Step recommendationStep;

    @OneToOne(mappedBy = "profile",
            fetch = FetchType.LAZY
    )
    private AccountProfileRelation accountProfileRelation;

    @OneToMany(mappedBy = "profile",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProfileBookRelation> profileBookRelations = new ArrayList<>();

    @Builder
    public Profile(String name, LocalDate birthDate, String imageUrl, Gender gender){
        this.name = name;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.gender = gender;
    }

    public int getBookViews(){
        return profileBookRelations.stream().mapToInt(ProfileBookRelation::getViews).sum();
    }

    public Account getAccount(){
        return accountProfileRelation.getAccount();
    }

    public void update(ProfileDto.PutProfile putProfile) {
        this.name = putProfile.getName();
        this.imageUrl = putProfile.getImageUrl();
        this.birthDate = putProfile.getBirthDate();
        this.gender = putProfile.getGender();
    }
}
