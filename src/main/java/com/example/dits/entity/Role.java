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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int roleId;
    @Column(length = 15)
    private String roleName;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_role"
            , joinColumns = @JoinColumn(name = "roleId"),
    inverseJoinColumns = @JoinColumn(name = "userId"))
    @ToString.Exclude
    private List<User> users;



}
