package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements VarGraph {

	// Représentation du graphe avec une hasmap : un sommet -> liste de ses arcs sortants
	private Map<String, List<Arc<String>>> adjacence;

	public GrapheHHAdj() {
		// On crée une hashmap vide
		this.adjacence = new HashMap<>();
	}

	@Override
	public List<Arc<String>> getSucc(String s) {
		// On retourne la liste des successeurs si le sommet existe, sinon on retourne une liste vide
		return adjacence.getOrDefault(s,new ArrayList<>());
	}

	@Override
	public void ajouterSommet(String noeud) {
		if (!adjacence.containsKey(noeud)) {
			// Si le sommet n'esxite pas deja on le met dans une liste
			adjacence.put(noeud, new ArrayList<Arc<String>>());
		}
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		//si les sommmets n'existent pas on les créer
		if (!adjacence.containsKey(source))
			ajouterSommet(source);
		if (!adjacence.containsKey(destination))
			ajouterSommet(destination);
		//on ajoute la destination à la liste des successeurs de la source
		adjacence.get(source).add(new Arc<>(valeur,destination));
		
	}

}
