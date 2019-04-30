package com.jpdacunha.media.batch.organizer.service;

import java.io.File;
import java.util.Date;

public interface MetaDatasReaderService {
	
	public Date readModifiedDate(File file);
	
	public String readModifiedDateAsString(File file);

}
