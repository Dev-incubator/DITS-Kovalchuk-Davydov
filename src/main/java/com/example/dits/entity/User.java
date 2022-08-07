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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int userId;

    @Column(length = 30)
    private String firstName;
    @Column(length = 30)
    private String lastName;
    @Column(length = 30)
    private String login;
    @Column
    private String password;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_role"
    , joinColumns = @JoinColumn(name = "userId")
    , inverseJoinColumns = @JoinColumn(name = "roleId"))
    @ToString.Exclude
    private Role role;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Statistic> statistics;

    public User(String firstName, String lastName, String login, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String firstName, String lastName, String login, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.role = role;
    }
}
