package com.jpdacunha.media.batch.core.configuration.service;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;

public interface ConfigurationService {
	
	public void verifyConfiguration() throws MediaBatchException;

}
