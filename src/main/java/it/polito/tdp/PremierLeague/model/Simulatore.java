package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {
	
	private Model model;
	
	private PriorityQueue<Match> queue;
	private List<Match> matches;
	
	private Integer Nmatch;
	private Integer NtotReporter;
	private Integer NmatchSottoSoglia;
	
	private Integer soglia;
	
	public void init(Integer N, Integer X, Model model) { 
		this.queue = new PriorityQueue<>();
		this.model = model;
		
		this.soglia = X;
		
		matches = new ArrayList<>(model.getAllMatches());
		Collections.sort(matches);
		for(Match m : matches) 
			this.queue.add(m);
		
		for(Team t : this.model.getTeams())
			t.setNumReporter(N);
		
		this.Nmatch = matches.size();
		this.NmatchSottoSoglia = 0;
		this.NtotReporter = 0;
	}
	
	public void simula() {
		Integer repoPartita = 0;
		Integer pene = 0;
		Team home;
		Team away;
		
		for(Match m : matches) {
		//while(!this.queue.isEmpty()) {
			//Match m = this.queue.poll();
			
			switch(m.getResultOfTeamHome()) {
				
			case 0:
				repoPartita = m.getTeamHome().getNumReporter() + m.getTeamAway().getNumReporter();
				this.NtotReporter += repoPartita;
				
				if(repoPartita<this.soglia)
					this.NmatchSottoSoglia++;
				
				break;
			
			case 1:		
				home = m.getTeamHome();
				away = m.getTeamAway();
				
				repoPartita = home.getNumReporter() + away.getNumReporter();
				this.NtotReporter += repoPartita;
				
				if(repoPartita<this.soglia)
					this.NmatchSottoSoglia++;
				
				if(home.getNumReporter()>0) {
					
					Integer prob = (int)(Math.random()*100);
					if(prob<50) {
						List<ElementoClassifica> teamMigliori = this.model.getTeamMigliori(home);

						if(teamMigliori.size()>0) {
							Integer squadra = (int)(Math.random()*teamMigliori.size());
							Team temp = teamMigliori.get(squadra).getTeam();

							home.setNumReporter(-1);
							temp.setNumReporter(1);
						}
					}
				}
				
				if(away.getNumReporter()>0) {
					
					Integer prob = (int)(Math.random()*100);
					if(prob<20) {
						List<ElementoClassifica> teamPeggiori = this.model.getTeamPeggiori(away);

						if(teamPeggiori.size()>0) {
							Integer squadra = (int)(Math.random()*teamPeggiori.size());
							Team temp = teamPeggiori.get(squadra).getTeam();
							
							Integer reporterBocciati;
							do {
								reporterBocciati = (int)(Math.random()*away.getNumReporter());
							} while(reporterBocciati == 0);
								
							away.setNumReporter(-reporterBocciati);
							temp.setNumReporter(reporterBocciati);
						}
					}
				}
				
				break;
				
			case -1:
				home = m.getTeamHome();
				away = m.getTeamAway();
				
				repoPartita = home.getNumReporter() + away.getNumReporter();
				this.NtotReporter += repoPartita;
				
				if(repoPartita<this.soglia)
					this.NmatchSottoSoglia++;
				
				if(away.getNumReporter()>0) {
					
					Integer prob = (int)(Math.random()*100);
					if(prob<50) {
						List<ElementoClassifica> teamMigliori = this.model.getTeamMigliori(away);

						if(teamMigliori.size()>0) {
							Integer squadra = (int)(Math.random()*teamMigliori.size());
							Team temp = teamMigliori.get(squadra).getTeam();

							away.setNumReporter(-1);
							temp.setNumReporter(1);
						}
					}
				}
				
				if(home.getNumReporter()>0) {
					
					Integer prob = (int)(Math.random()*100);
					if(prob<20) {
						List<ElementoClassifica> teamPeggiori = this.model.getTeamPeggiori(home);

						if(teamPeggiori.size()>0) {
							Integer squadra = (int)(Math.random()*teamPeggiori.size());
							Team temp = teamPeggiori.get(squadra).getTeam();
							
							Integer reporterBocciati;
							do {
								reporterBocciati = (int)(Math.random()*home.getNumReporter());
							} while(reporterBocciati == 0);
								
							home.setNumReporter(-reporterBocciati);
							temp.setNumReporter(reporterBocciati);
						}
					}
				}
				
				break;
			}
			
			System.out.println(pene++);
		}
	}
	
	public Integer getReporterMedi() {
		return this.NtotReporter/this.Nmatch;
	}
	
	public Integer getNumeroPartiteCritiche() {
		return this.NmatchSottoSoglia;
	}

}
