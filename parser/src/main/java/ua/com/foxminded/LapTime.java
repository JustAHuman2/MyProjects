package ua.com.foxminded;

import java.time.Duration;
import java.time.LocalDateTime;

public class LapTime {

	private LocalDateTime startTime;
	private LocalDateTime finishTime;
	private Duration resultTime;

	public LapTime(LocalDateTime startTime, LocalDateTime finishTime) {
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.resultTime = Duration.between(startTime, finishTime);
	}

	public Duration getResultTime() {
		return resultTime;
	}

	@Override
	public String toString() {
		return String.format("%-1d:%02d.%-3.3s", getResultTime().toMinutes(), getResultTime().getSeconds() % 60,
				getResultTime().getNano());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LapTime) || (this.hashCode() != obj.hashCode()))
			return false;
		if (this == obj)
			return true;
		LapTime thatLapTime = (LapTime) obj;
		if (this.getResultTime().equals(thatLapTime.getResultTime())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) (this.getResultTime().toNanos());
	}
}