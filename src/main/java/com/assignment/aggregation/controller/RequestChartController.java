package com.assignment.aggregation.controller;

import com.opencsv.CSVReader;
import com.opencsv.ColonReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RequestChartController {

    @GetMapping("/requestChart")
    public ChartData getChartData(@RequestParam("requestName") String requestName) {
        // Define the chart data
        System.out.println(requestName);
        LocalDateTime dateTime = LocalDateTime.parse(requestName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = dateTime.format(formatter);

        List<Float> data = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        CSVReader reader = null;
        try {
            reader = new ColonReader(new FileReader("/Users/sandhya/download/" + formattedDateTime + ".perf"), 3);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        List<String[]> rows = null;
        try {
            rows = reader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (CsvException e) {
            throw new RuntimeException(e.getMessage());
        }
        for (String[] row : rows) {
            if (6 == row.length) {
                String cpuTime = row[3].trim();
                String count = row[5].trim();
                if (!cpuTime.equals("~")) {
                    String testName = StringUtils.trimAllWhitespace(row[0].trim() + "." + row[1].trim()).replaceAll("\\|","");
                    float executionTime = Float.parseFloat(cpuTime);
                    int c = Integer.parseInt(StringUtils.trimAllWhitespace(count.replaceAll("\\|","")));
                    data.add(executionTime);
                    counts.add(c);
                    labels.add(testName);
                }
            }
        }


        return new ChartData(labels, data,counts);
    }

    static class ChartData {
        private final List<String> labels;
        private final List<Float> timeTaken;
        private final List<Integer> count;

        public ChartData(List<String> labels, List<Float> timeTaken,List<Integer> count) {
            this.labels = labels;
            this.timeTaken = timeTaken;
            this.count = count;
        }

        public List<String> getLabels() {
            return labels;
        }

        public List<Float> getTimeTaken() {
            return timeTaken;
        }
        public List<Integer> getCount() {
            return count;
        }
    }
}

