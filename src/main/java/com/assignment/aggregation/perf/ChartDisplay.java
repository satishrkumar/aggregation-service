package com.assignment.aggregation.perf;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartDisplay extends JFrame {

    public ChartDisplay() {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Series 1", "Category 1");
        dataset.addValue(2.0, "Series 1", "Category 2");
        dataset.addValue(3.0, "Series 1", "Category 3");
        dataset.addValue(4.0, "Series 1", "Category 4");
        dataset.addValue(5.0, "Series 1", "Category 5");

        // Create a chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Chart Demo",  // chart title
                "Category",   // domain axis label
                "Value",      // range axis label
                dataset       // data
        );

        // Create a chart panel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chart Demo");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChartDisplay();
        });
    }
}

