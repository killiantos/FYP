package astra.agents;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import astra.core.ActionParam;
import astra.core.Module;
import graph.core.DirectedGraph;
import graph.core.Vertex;
import graph.gui.Coordinate;
import graph.gui.JGraphPanel;
import graph.impl.DirectedAdjacencyListGraph;

public class Map extends Module {
	private class MapView extends JFrame {
		JGraphPanel<String, String> panel;
		
		public MapView() {
			setLayout(new BorderLayout());
			panel = new JGraphPanel<String, String>(graph, coordinates, 10, 10, 200, 200);
			JScrollPane pane = new JScrollPane(panel);
			add(pane);
			pack();
		}
		
		public void refresh() {
			panel.repaint();
		}
	}
	
	private static class Location {
		double x;
		double y;
		Vertex<String> vertex;
		
		public Location(Vertex<String> vertex, double x, double y) {
			this.x = x;
			this.y = y;
			this.vertex = vertex;
		}
		
		public String toString() {
			return vertex.element()+ ":(" + x + ", " + y + ")";
		}

		public double distance(double x, double y) {
			return Math.sqrt(Math.pow(this.x-x, 2) + Math.pow(this.y - y, 2));
		}
	}
	
	private DirectedGraph<String, String> graph = new DirectedAdjacencyListGraph<String, String>();
	HashMap<Vertex<String>, Coordinate> coordinates = new HashMap<Vertex<String>, Coordinate>();
	List<Location> locations = new ArrayList<Location>();
	MapView mapView = new MapView();
	
	private String direction="South";
	
	private String opposite(String direction) {
		switch (direction) {
		case "North":
			return "South";
		case "South":
			return "North";
		case "East":
			return "West";
		case "West":
			return "East";
		}
		return null;
	}
	
	@ACTION
	public boolean display() {
		mapView.setTitle("Robot: " +agent.name());
		mapView.setSize(400, 400);
		mapView.setVisible(true);
		return true;
	}
	
	@ACTION
	public boolean recordLocation(String name, int locId, double x, double y, String type, ActionParam<Integer> loc) {
		Vertex<String> vertex = graph.insertVertex(type);
		if (locId > -1) {
			graph.insertEdge(locations.get(locId).vertex, vertex, direction);
			graph.insertEdge(vertex,  locations.get(locId).vertex, opposite(direction));
		}
		
		loc.set(locations.size());
		locations.add(new Location(vertex,x,y));
		
		coordinates.put(vertex, new Coordinate((int) x*20, (int) y*20));
		mapView.refresh();
		return true;
	}
	
	@ACTION
	public boolean setOrientation(String direction) {
		this.direction = direction;
		return true;
	}
	
	@TERM
	public String getOrientation() {
		return direction;
	}
	
	@TERM
	public int findLocation(double x, double y, double threshold) {
		for (int i=0; i <locations.size(); i++) {
			double dist =locations.get(i).distance(x, y); 
//			System.out.println("["+i+ "] distance: " + dist);
			if (dist < threshold) return i;
		}
		return -1;
	}
}
