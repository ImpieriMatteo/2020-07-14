package it.polito.tdp.PremierLeague.model;

public class ElementoClassifica implements Comparable<ElementoClassifica>{

	private Team team; 
	private Integer diffPunti;
	
	public ElementoClassifica(Team team, Integer diffPunti) {
		this.team = team;
		this.diffPunti = diffPunti;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getDiffPunti() {
		return diffPunti;
	}

	public void setDiffPunti(Integer diffPunti) {
		this.diffPunti = diffPunti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementoClassifica other = (ElementoClassifica) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	@Override
	public int compareTo(ElementoClassifica other) {
		return this.diffPunti.compareTo(other.diffPunti);
	}

	@Override
	public String toString() {
		return this.team.getName()+"("+this.diffPunti+")";
	}
	
}
