package com.jpdacunha.media.batch.organizer.model.strategy;

import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;

public class LastModififiedClassificationStrategy implements ClassificationStrategy {

	@Override
	public String getClassificationPath() throws UnrelevantClassificationStrategyException {
		return "LastModififiedClassificationStrategy";
	}

}
