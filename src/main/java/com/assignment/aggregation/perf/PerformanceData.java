package com.assignment.aggregation.perf;

import lombok.Data;

@Data
public class PerformanceData {
    private String functionName;
    private int iteration;
    private float time;
}
