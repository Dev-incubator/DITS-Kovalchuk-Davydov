package com.example.dits.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Statistic {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statisticId;

    @Column
    private Date date;

    @Column
    private boolean correct;


    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="questionId")
    private Question question;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="userId")
    private User user;

    public Statistic(Date date, boolean correct, Question question, User user) {
        this.date = date;
        this.correct = correct;
        this.question = question;
        this.user = user;
    }
}
