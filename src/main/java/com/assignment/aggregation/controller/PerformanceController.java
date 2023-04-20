package com.assignment.aggregation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class PerformanceController {
    @GetMapping("/performance")
    public List<PerformanceEntry> getPerformanceData() throws IOException {
        final List<PerformanceController.PerformanceEntry> result = new ArrayList<>();
        Files.list(Path.of("/Users/sandhya/download")).forEach(path -> {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                String line = br.readLine();
                Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                Matcher matcher = pattern.matcher(line);
                Pattern patternFileName = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})-(\\d{2}-\\d{2}-\\d{2})");
                Matcher matcherFileName = patternFileName.matcher(path.toFile().getName());

                if (matcher.find() && matcherFileName.find()) {
                    float number = Float.parseFloat(matcher.group());
                    String dateString = matcherFileName.group(1);
                    String timeString = matcherFileName.group(2);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    LocalDateTime dateTime = LocalDateTime.parse(dateString + "-" + timeString, formatter);
                    PerformanceController.PerformanceEntry performanceEntry = new PerformanceController.PerformanceEntry(dateTime, number);
                    result.add(performanceEntry);
                } else {
                    System.out.println("No number found in first line of file " + path.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    public static class PerformanceEntry {
        private final LocalDateTime date;
        private final float timeTaken;

        public PerformanceEntry(LocalDateTime date, float timeTaken) {
            this.date = date;
            this.timeTaken = timeTaken;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public float getTimeTaken() {
            return timeTaken;
        }
    }
}
