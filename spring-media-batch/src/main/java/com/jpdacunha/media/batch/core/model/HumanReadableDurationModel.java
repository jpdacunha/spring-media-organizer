package com.jpdacunha.media.batch.core.model;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HumanReadableDurationModel {
	
	private Duration duration;
	
	public HumanReadableDurationModel(Instant start, Instant end) {
		super();
		this.duration = Duration.between(start, end);
	}

	public String toHumanReadable() {
		
	    List<String> parts = new ArrayList<>();
	    long days = duration.toDaysPart();
	    if (days > 0) {
	        parts.add(plural(days, "day"));
	    }
	    int hours = duration.toHoursPart();
	    if (hours > 0 || !parts.isEmpty()) {
	        parts.add(plural(hours, "hour"));
	    }
	    int minutes = duration.toMinutesPart();
	    if (minutes > 0 || !parts.isEmpty()) {
	        parts.add(plural(minutes, "minute"));
	    }
	    int seconds = duration.toSecondsPart();
	    parts.add(plural(seconds, "second"));
	    return String.join(", ", parts);
	    
	}

	private String plural(long num, String unit) {
	    return num + " " + unit + (num == 1 ? "" : "s");
	}

}
