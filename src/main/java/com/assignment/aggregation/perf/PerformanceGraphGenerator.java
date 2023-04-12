package com.assignment.aggregation.perf;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import ch.qos.logback.core.util.FileUtil;
import com.opencsv.CSVReader;
import com.opencsv.ColonReader;
import com.opencsv.exceptions.CsvException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.util.FileCopyUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public class PerformanceGraphGenerator extends JFrame {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PerformanceGraphGenerator();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        });
    }
    public PerformanceGraphGenerator() throws IOException, CsvException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Files.list(Path.of("/Users/sandhya/download")).forEach(path -> {
            CSVReader reader = null;
            try {
                reader = new ColonReader(new FileReader(path.toAbsolutePath().toString()),3);
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
                if(6==row.length) {
                    String cpuTime = row[4].trim();
                    if (!cpuTime.equals("~")) {
                        String testName = row[1].trim();
                        float executionTime = Float.parseFloat(cpuTime);
                        dataset.addValue(executionTime, "Execution Time", testName);
                    }
                }
            }
        });


        JFreeChart chart = ChartFactory.createBarChart(
                "Performance Graph",
                "Test Name",
                "Execution Time (ms)",
                dataset
        );
        // Create a chart panel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(800, 400));
        // Create a scroll pane and add the chart panel to it
        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Create a panel and add the scroll pane to it
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Enable chart interactivity
        //chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);

        setContentPane(panel);
        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Performance Demo");
        // Set the frame to full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //ChartUtils.saveChartAsJPEG(new File("performance-graph.png"), chart, 2400, 1200);
       // ChartUtilities.saveChartAsPNG(new File("performance-graph.png"), chart, 600, 400);
    }
    public static float findAverage(float[] numbers) {
        float sum = 0;
        for (float number : numbers) {
            sum += number;
        }
        float average = sum / numbers.length;
        return average;
    }
}
