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
@Table(name = "topic")
public class Topic {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topicId;

    public Topic(String name) {
        this.name = name;
    }

    public Topic(String description, String name) {
        this.description = description;
        this.name = name;
    }

    @Column(length = 100)
    private String description;

    @Column(length = 20)
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade ={CascadeType.ALL,CascadeType.PERSIST},
    orphanRemoval = true)
    @ToString.Exclude
    private List<Test> testList;

}
