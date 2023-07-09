package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.book.Book;
import com.waywalkers.kbook.domain.book.BookRepository;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.domain.profile.ProfileRepository;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelation;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelationRepository;
import com.waywalkers.kbook.dto.ProfileBookRelationDto;
import com.waywalkers.kbook.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileBookRelationService {
    private final BookRepository bookRepository;
    private final ProfileBookRelationRepository profileBookRelationRepository;
    private final ProfileRepository profileRepository;
    private final AccountProfileRelationRepository accountProfileRelationRepository;


    public ResultDto createProfileBookRelation(long accountId, long profileId, long bookId) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        ProfileBookRelation existProfileBookRelation = profileBookRelationRepository.findByProfile_IdAndBook_Id(profileId, bookId).orElse(null);
        if(existProfileBookRelation != null){
            throw new EntityExistsException("profileBookRelation");
        }
        Profile profile = profileRepository.findById(profileId).orElseThrow(()->new EntityNotFoundException("profile"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("book"));
        ProfileBookRelation profileBookRelation = ProfileBookRelation.builder()
                .profile(profile)
                .book(book)
                .build();
        profileBookRelationRepository.save(profileBookRelation);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(profileBookRelation.getId())
                .build();
    }

    public ResultDto deleteProfileBookRelation(long accountId, long profileId, long bookId) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        profileBookRelationRepository.deleteByProfile_IdAndBook_Id(profileId, bookId);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResultDto putProfileBookRelation(long accountId, long profileId, long bookId, ProfileBookRelationDto.PutProfileBookRelation putProfileBookRelation) {
        accountProfileRelationRepository.findByAccount_IdAndProfile_Id(accountId, profileId).orElseThrow(()-> new EntityNotFoundException("accountProfileRelation"));
        ProfileBookRelation profileBookRelation = profileBookRelationRepository.findByProfile_IdAndBook_Id(profileId, bookId).orElseThrow(()-> new EntityNotFoundException("profileBookRelation"));
        profileBookRelation.update(putProfileBookRelation);
        return ResultDto.builder()
                .data(profileBookRelation.getId())
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
