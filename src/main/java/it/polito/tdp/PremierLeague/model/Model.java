package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Map<Integer, Team> idMapTeam;
	private Map<Team, Integer> classifica;
	private PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.idMapTeam = new HashMap<>();
		this.dao = new PremierLeagueDAO();
		
		this.dao.listAllTeams(idMapTeam);
	}
	
	public String creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.classifica = new HashMap<>();
		List<Match> matches = new ArrayList<>(this.dao.listAllMatches(idMapTeam));
		
		Graphs.addAllVertices(this.grafo, this.idMapTeam.values());
		
		for(Team t : this.grafo.vertexSet()) 
			this.classifica.put(t, 0);
		
		for(Match m : matches) {
			if(m.getResultOfTeamHome()==1) {
				Integer punteggio = this.classifica.get(m.getTeamHome()) + 3;
				this.classifica.put(m.getTeamHome(), punteggio);
			}
			else if(m.getResultOfTeamHome()==-1) {
				Integer punteggio = this.classifica.get(m.getTeamAway()) + 3;
				this.classifica.put(m.getTeamAway(), punteggio);
			}
			else if(m.getResultOfTeamHome()==0) {
				Integer punteggioHome = this.classifica.get(m.getTeamHome()) + 1;
				this.classifica.put(m.getTeamHome(), punteggioHome);
				Integer punteggioAway = this.classifica.get(m.getTeamAway()) + 1;
				this.classifica.put(m.getTeamAway(), punteggioAway);
			}
		}
		
		for(Team t1 : this.classifica.keySet()) {
			for(Team t2 : this.classifica.keySet()) {
				if(!t1.equals(t2) && (!this.grafo.containsEdge(t1, t2) || !this.grafo.containsEdge(t2, t1))) {
					Integer diff = this.classifica.get(t1)-this.classifica.get(t2);
					if(diff>0) 
						Graphs.addEdgeWithVertices(this.grafo, t1, t2, diff);
					else if(diff<0)
						Graphs.addEdgeWithVertices(this.grafo, t2, t1, -diff);
				}
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public Set<Team> getTeams() {
		return this.classifica.keySet();
	}
	
	public List<ElementoClassifica> getTeamMigliori(Team team) {
		List<ElementoClassifica> semiClassifica = new ArrayList<>();
		ElementoClassifica teamScelto = new ElementoClassifica(team, this.classifica.get(team));
		
		for(Team t : this.classifica.keySet()) {
			Integer diff = this.classifica.get(t)-teamScelto.getDiffPunti();
			
			if(diff>0) 
				semiClassifica.add(new ElementoClassifica(t, diff));	
		}
		
		return semiClassifica;
	}
	
	public List<ElementoClassifica> getTeamPeggiori(Team team) {
		List<ElementoClassifica> semiClassifica = new ArrayList<>();
		ElementoClassifica teamScelto = new ElementoClassifica(team, this.classifica.get(team));
		
		for(Team t : this.classifica.keySet()) {
			Integer diff = this.classifica.get(t)-teamScelto.getDiffPunti();
			
			if(diff<0) 
				semiClassifica.add(new ElementoClassifica(t, -diff));	
		}
		
		return semiClassifica;
	}
}
