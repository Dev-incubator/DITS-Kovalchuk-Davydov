package com.example.dits.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Test {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int testId;

    @Column(length = 100)
    private String description;

    @Column(length = 50)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "topicId")
    private Topic topic;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
    orphanRemoval = true)
    @ToString.Exclude
    private List<Question> questions;
}
