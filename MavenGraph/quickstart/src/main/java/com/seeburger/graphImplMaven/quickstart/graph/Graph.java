package com.seeburger.graphImplMaven.quickstart.graph;


import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "graph")
@XmlAccessorType (XmlAccessType.FIELD)
public class Graph <V>{

	@XmlElement(name = "verteces with edges")
	private HashMap<V, ArrayList<Edge<V>>> adjacencyList;

	/**
	 * This list holds all the vertices so that we can iterate over them in the
	 * toString function
	 */
	@XmlElement(name = "verteces list")
	private ArrayList<V> vertexList;

	@XmlElement(name = "is directed")
	private boolean directed;
	
	public Graph(boolean isDirected) {
		this.directed = isDirected;
		this.adjacencyList = new HashMap<V, ArrayList<Edge<V>>>();
		this.vertexList = new ArrayList<V>();
	}
	
	public Graph() {}
	
	public void add(V vertex, ArrayList<Edge<V>> connectedVertices) {
		// Add the new vertex to the adjacencyList with it's list of connected
		// nodes
		adjacencyList.put(vertex, connectedVertices);
		vertexList.add(vertex);
		// If this is an undirected graph, every edge needs to represented
		// twice, once in the added vertex's list and once in the list of each
		// of the vertex's connected to the added vertex

			for (Edge<V> vertexConnectedToAddedVertex : connectedVertices) {
				ArrayList<Edge<V>> correspondingConnectedList = adjacencyList
						.get(vertexConnectedToAddedVertex.getVertex());
				// The added vertex's connections might not be represented in
				// the Graph yet, so we implicitly add them
				if (correspondingConnectedList == null) {
					adjacencyList.put(vertexConnectedToAddedVertex.getVertex(),
							new ArrayList<Edge<V>>());
					vertexList.add(vertexConnectedToAddedVertex.getVertex());
					correspondingConnectedList = adjacencyList
							.get(vertexConnectedToAddedVertex.getVertex());
				}
				
				if (!directed) {
					// The weight from one vertex back to another in an undirected
					// graph is equal
					int weight = vertexConnectedToAddedVertex.getWeight();
					correspondingConnectedList.add(new Edge<V>(vertex, weight));
				}
			}
		
	}
	
	public boolean addArc(V source, V end, int weight) {
		if (!directed) {
			return false;
		}

		if (!adjacencyList.containsKey(source)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(end, weight));
			add(source, tempList);
			return true;
		}
		
		if (!adjacencyList.containsKey(end)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			add(end, tempList);
		}
		

		adjacencyList.get(source).add(new Edge<V>(end, weight));
		return true;
	}
	
	
	public boolean addEdge(V vertexOne, V vertexTwo, int weight) {
		if (directed) {
			return false;
		}
		
		if (!adjacencyList.containsKey(vertexOne)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(vertexTwo, weight));
			add(vertexOne, tempList);
			return true;
		}

		if (!adjacencyList.containsKey(vertexTwo)) {
			ArrayList<Edge<V>> tempList = new ArrayList<Edge<V>>();
			tempList.add(new Edge<V>(vertexOne, weight));
			add(vertexTwo, tempList);
			return true;
		}

		adjacencyList.get(vertexOne).add(new Edge<V>(vertexTwo, weight));
		adjacencyList.get(vertexTwo).add(new Edge<V>(vertexOne, weight));
		return true;
	}
	
	
	
	public HashMap<V, ArrayList<Edge<V>>> getAdjacencyList() {
		return this.adjacencyList;
	}
	
	public void setAdjacencyList(HashMap<V, ArrayList<Edge<V>>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	
	public String toString() {
		String s = "";
		for (V vertex : vertexList) {
			s += vertex.toString();
			s += " : ";
			s += adjacencyList.get(vertex);
			s += "\n";
		}
		return s;
	}
	
	
	
	
	
}
