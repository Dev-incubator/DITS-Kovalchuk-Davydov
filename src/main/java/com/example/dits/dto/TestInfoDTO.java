package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestInfoDTO implements Serializable {
    private int testId;
    private String name;
    private String description;
    private int topicId;
}
