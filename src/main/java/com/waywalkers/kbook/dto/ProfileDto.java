package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.Gender;
import com.waywalkers.kbook.domain.profile.Profile;
import lombok.*;

import java.time.LocalDate;

public class ProfileDto {
    @Data
    public static class ListOfProfiles{
        private long id;
        private String name;
        private String imageUrl;
    }

    @Data
    public static class ProfileDetail{
        private long accountId;
        private long id;
        private String name;
        private LocalDate birthDate;
        private Gender gender;
    }

    @Data
    public static class AccountProfile{
        private long id;
        private String name;
        private LocalDate birthDate;
        private Gender gender;
        private Integer views;

        public AccountProfile(Profile profile){
            this.id = profile.getId();
            this.name = profile.getName();
            this.birthDate = profile.getBirthDate();
            this.gender = profile.getGender();
            this.views = profile.getBookViews();
        }
    }

    @Builder
    @Data
    public static class EvaluateProfile{
        private Long id;
        private String name;
    }

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class PostProfile {
        private String name;
        private String imageUrl;
        private LocalDate birthDate;
        private Gender gender;
    }

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class PutProfile {
        private String name;
        private String imageUrl;
        private LocalDate birthDate;
        private Gender gender;
    }

}
