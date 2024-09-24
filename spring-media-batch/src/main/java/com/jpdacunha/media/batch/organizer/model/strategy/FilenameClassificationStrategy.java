package com.jpdacunha.media.batch.organizer.model.strategy;

import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;

public class FilenameClassificationStrategy implements ClassificationStrategy {

	@Override
	public String getClassificationPath() throws UnrelevantClassificationStrategyException {
		return "FilenameClassificationStrategy";
	}

}
