package com.example.dits.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int questionId;

    @Column
    private String description;

    public Question(String description) {
        this.description = description;
    }

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
    orphanRemoval = true)
    @ToString.Exclude
    List<Answer> answers;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "testId")
    private Test test;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY,cascade = CascadeType.MERGE,
    orphanRemoval = true)
    private List<Statistic> statistics;

    public Question(String description, Test test) {
        this.description = description;
        this.test = test;
    }
}
