package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestStatisticByUser implements Comparable<TestStatisticByUser>{
    private String testName;
    private int count;
    private int avgProc;

    @Override
    public int compareTo(TestStatisticByUser o) {
        return o.getAvgProc()-this.getAvgProc();
    }
}
