package hr.fer.zemris.java.hw14.servleti;

import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * This class creates piechart from given dataset. Dataset is
 * in form of a map, where key is the name of things needed to be shown, 
 * and values are percentage of those things.
 * User can then ask for created piechart with {@link #getChart()}.
 * 
 * @author Lucija Valentić
 *
 */
public class PieChart{

	/**
	 * Represents internal chart
	 */
    private JFreeChart chart;
    
    /**
     * Constructor
     * @param applicationTitle String
     * @param forDataset Map<String, Integer>
     */
    public PieChart(String applicationTitle, Map<String, Integer> forDataset) {
        
        // This will create the dataset
        PieDataset dataset = createDataset(forDataset);
        // based on the dataset we create the chart
        chart = createChart(dataset);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        
    }

    /**
     * Creates a sample dataset
     * @param forDataset 
     */
    private  PieDataset createDataset(Map<String, Integer> forDataset) {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for(String s : forDataset.keySet()) {
        	result.setValue(s, forDataset.get(s));
        }
        
        return result;

    }

    /**
     * Creates a chart
     * @param dataset PieDataser
     * @return JFreeChart chart
     */
    private JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            null,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

    /**
     * Returns this.chart
     * @return JFreeChart chart
     */
	public JFreeChart getChart() {
		return chart;
	}

    
}