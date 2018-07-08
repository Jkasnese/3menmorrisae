package environment;


import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	private List<XYSeries> series =  Collections.synchronizedList(new ArrayList<XYSeries>());
	private XYSeriesCollection data;
	private JFreeChart graph;
	
	public GraphGenerator(String title) {
		super(title);
		series = new ArrayList<XYSeries>();
	}
	
	public void createSeries(String series_name) {
		XYSeries new_series = new XYSeries(series_name);
		series.add(new_series);
	}
	
	public void addValue(double x, double y[]) {	
		for(int i = 0; i < y.length; i++) {
			series.get(i).add(x, y[i]);
		}
	}
	
	public void generateGraph(String y_axis_title) {
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		this.data = new XYSeriesCollection();
		
		Color[] clr_array = {Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.BLACK, Color.MAGENTA};

		
		for(int i = 0; i < series.size(); i++) {
			this.data.addSeries(series.get(i));
			renderer.setSeriesPaint(i, clr_array[i]);
		}
		
		this.graph = ChartFactory.createXYLineChart("", "Generations", y_axis_title, data, PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = graph.getXYPlot();
		
		
		plot.setOutlinePaint(Color.BLACK);
		plot.setRenderer(renderer);
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible(false);
		
		plot.setDomainGridlinesVisible(false);
		
	
		final ChartPanel chartPanel = new ChartPanel(graph);
		chartPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));
		setContentPane(chartPanel);
	}
	
	

	public void savePNGFile(String file_name){

		try {
			OutputStream file = new FileOutputStream(file_name + ".png");
			ChartUtilities.writeChartAsPNG(file, graph, 1920, 1080);
			file.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
