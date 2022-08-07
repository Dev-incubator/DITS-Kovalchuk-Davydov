package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics{
    private String firstName;
    private String lastName;
    private String login;
    private List<TestStatistic> testStatisticList;

}
