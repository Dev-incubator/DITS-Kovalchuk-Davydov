package com.example.dits.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int answerId;
    @Column
    private String description;
    @Column
    private boolean correct;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "questionId")
    private Question question;

    public Answer(String description, boolean correct, Question question) {
        this.description = description;
        this.correct = correct;
        this.question = question;
    }

    public Answer(String description, boolean correct) {
        this.description = description;
        this.correct = correct;
    }
}
