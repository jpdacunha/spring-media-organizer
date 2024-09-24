package com.jpdacunha.media.batch.organizer.model.strategy;

import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;

public interface ClassificationStrategy {
	
	public String getClassificationPath() throws UnrelevantClassificationStrategyException;

}
