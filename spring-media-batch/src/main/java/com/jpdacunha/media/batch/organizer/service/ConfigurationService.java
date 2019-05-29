package com.jpdacunha.media.batch.organizer.service;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;

public interface ConfigurationService {
	
	public void verifyConfiguration() throws MediaBatchException;

}
