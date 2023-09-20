package com.jpdacunha.media.batch.removeduplicatesfotos.model;

import com.jpdacunha.media.batch.core.model.HumanReadableFileSizeModel;

public class ListRemovalReportModel {
	
	private String totalSavedSize;
	private int nbFilesDeleted;
	
	public ListRemovalReportModel(double totalSavedSize, int nbFilesDeleted) {
		super();
		HumanReadableFileSizeModel humanReadableFileSizeModel = new HumanReadableFileSizeModel(totalSavedSize);
		this.totalSavedSize = humanReadableFileSizeModel.toHumanReadable();
		this.nbFilesDeleted = nbFilesDeleted;
	}

	public String getTotalSavedSize() {
		return totalSavedSize;
	}

	public int getNbFilesDeleted() {
		return nbFilesDeleted;
	}
	
}
