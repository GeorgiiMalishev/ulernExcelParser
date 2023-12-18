import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Chart extends JFrame {

    public Chart(String title) {
        super(title);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static CategoryDataset createBarDataset(Map<String, Double> data) {
        var dataset = new DefaultCategoryDataset();
        for (var entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey(), "");
        }

        return dataset;
    }

    private static DefaultPieDataset<String> createPieDataset(Map<String, Double> data) {
        var dataset = new DefaultPieDataset<String>();
        for (var entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }

    public void showBarChart(String titleX, String titleY, Map<String, Double> data){
        SwingUtilities.invokeLater(() -> {
            JFreeChart chart = ChartFactory.createBarChart(
                    this.getTitle(),
                    titleX,
                    titleY,
                    createBarDataset(data),
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
        pack();
        setVisible(true);
    });}

    public void showPieChart(Map<String, Double> data){
        SwingUtilities.invokeLater(() -> {
            JFreeChart chart = ChartFactory.createPieChart(
                    this.getTitle(),
                    createPieDataset(data),
                    true,
                    true,
                    false
            );
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            setContentPane(chartPanel);
            pack();
            setVisible(true);
        });
    }
}