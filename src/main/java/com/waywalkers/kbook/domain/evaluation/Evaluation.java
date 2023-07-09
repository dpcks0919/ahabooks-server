package com.waywalkers.kbook.domain.evaluation;

import com.google.gson.annotations.SerializedName;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.record.Record;
import com.waywalkers.kbook.dto.EvaluationDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="evaluation")
@Entity
public class Evaluation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    @SerializedName(value = "absolute_score")
    @Column(name = "absolute_score")
    private double absoluteScore;

    @SerializedName(value = "letters_per_min")
    @Column(name = "letters_per_min")
    private double lettersPerMin;

    @SerializedName(value = "total_letters")
    @Column(name = "total_letters")
    private int totalLetters;

    @SerializedName(value = "total_time")
    @Column(name = "total_time")
    private double totalTime;

    @SerializedName(value = "total_words")
    @Column(name = "total_words")
    private int totalWords;

    @SerializedName(value = "words_per_min")
    @Column(name = "words_per_min")
    private double wordsPerMin;

    @Column(name = "misreading_rate")
    private int misreadingRate;

    @Column(name = "expert_evaluation")
    private String expertEvaluation;

    @Column(name = "speedreading_rate")
    private int speedreadingRate;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @Builder
    public Evaluation(int misreadingRate, int speedreadingRate, String expertEvaluation, Record record){
        this.misreadingRate = misreadingRate;
        this.speedreadingRate = speedreadingRate;
        this.expertEvaluation = expertEvaluation;
        this.record = record;
    }

    public void createExpertEvaluation(EvaluationDto.PutEvaluation putEvaluation){
        if(putEvaluation.getExpertEvaluation() != null){
            this.expertEvaluation = putEvaluation.getExpertEvaluation();
        }
    }

    public void update(Evaluation evaluation){
        this.absoluteScore = evaluation.getAbsoluteScore();
        this.lettersPerMin = evaluation.getLettersPerMin();
        this.totalLetters = evaluation.getTotalLetters();
        this.totalTime = evaluation.getTotalTime();
        this.totalWords = evaluation.getTotalWords();
        this.wordsPerMin = evaluation.getWordsPerMin();
    }
}
