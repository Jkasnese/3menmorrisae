package environment;


import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphGenerator extends JFrame{

	private static final long serialVersionUID = 1L;
	private final XYSeries max_fit_series, avg_fit_series;
	private XYSeriesCollection data;
	private JFreeChart graph;
	
	public GraphGenerator(String title) {
		super(title);
		max_fit_series = new XYSeries("Max Fitness");
		avg_fit_series = new XYSeries("Average Fitness");
		
	}
	
	public void add_value(double x, double y, int series_num) {
		if(series_num == 0) {
			this.max_fit_series.add(x, y);
		}
		else
			this.avg_fit_series.add(x, y);
	}
	
	
	public void generate_graph() {
		
		this.data = new XYSeriesCollection();
		data.addSeries(avg_fit_series);
		data.addSeries(max_fit_series);
		
		this.graph = ChartFactory.createXYLineChart("", "Generations", "Fitness", data, PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = graph.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.BLUE);
		
		plot.setOutlinePaint(Color.WHITE);
		plot.setRenderer(renderer);
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible(false);
		
		plot.setDomainGridlinesVisible(false);
		
	
		final ChartPanel chartPanel = new ChartPanel(graph);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}
	
	

	public void save_png_file(String file_name){

		try {
			OutputStream file = new FileOutputStream(file_name + ".png");
			ChartUtilities.writeChartAsPNG(file, graph, 1200, 900);
			file.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
