package ua.com.foxminded;

public class Racer {

	private String abbreviation;
	private String name;
	private String team;
	private LapTime lapTime;

	public Racer(String abbreviation, String name, String team, LapTime lapTime) {
		this.abbreviation = abbreviation;
		this.name = name;
		this.team = team;
		this.lapTime = lapTime;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public String getTeam() {
		return team;
	}

	public String getLapTime() {
		return lapTime.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof Racer) || (this.hashCode() != obj.hashCode()))
			return false;
		if (this == obj)
			return true;
		Racer thatRacer = (Racer) obj;
		if ((this.getAbbreviation().equals(thatRacer.getAbbreviation()) && (this.getName().equals(thatRacer.getName()))
				&& (this.getTeam().equals(thatRacer.getTeam())) && (this.lapTime.equals(thatRacer.lapTime)))) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) (this.getAbbreviation().length() * 31 + this.getName().length() * 13 + this.getTeam().length() * 7
				+ this.lapTime.getResultTime().toNanos());
	}
}