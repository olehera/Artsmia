package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, ArtObject>();
	}
	
	public void creaGrafo() {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		dao.listObjects(idMap);
		
		// aggiungo i vertici
		Graphs.addAllVertices(graph, idMap.values());
		
		// aggiungo gli archi
		for (Adiacenza a : dao.listAdiacenze()) {
			ArtObject source = idMap.get(a.getO1());
			ArtObject dest = idMap.get(a.getO2());
			Graphs.addEdge(graph, source, dest, a.getPeso());
		}
		
	}
	
	public int getVertexSize() {
		return graph.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return graph.edgeSet().size();
	}
	
}
