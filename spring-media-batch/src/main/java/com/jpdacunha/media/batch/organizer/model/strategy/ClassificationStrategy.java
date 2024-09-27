package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;

public interface ClassificationStrategy {
	
	public static final Locale DEFAULT_LOCALE = Locale.FRANCE;
	
	public Path getClassificationPath(File toOrganize, File destDir) throws UnrelevantClassificationStrategyException;

}
