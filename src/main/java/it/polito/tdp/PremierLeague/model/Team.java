package it.polito.tdp.PremierLeague.model;

public class Team implements Comparable<Team>{
	
	Integer teamID;
	String name;
	
	Integer numReporter;

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		this.numReporter = 0;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumReporter() {
		return numReporter;
	}

	public void setNumReporter(Integer numReporter) {
		this.numReporter += numReporter;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
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
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Team other) {
		return this.name.compareTo(other.name);
	}
	
}
