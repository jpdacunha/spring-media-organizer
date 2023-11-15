package com.jpdacunha.media.batch.core.database.model;

import java.util.Date;
import java.util.UUID;

public class Cursor {
	
	private static final String DEFAULT_EXECUTION_TIME_VALUE = "0";
	
	private String id;
	private String path;
	private Date creationDate;
	private Date lastExecutionDate;
	private String executionTime;

	public Cursor(String path, Date creationDate) {
		super();
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.creationDate = creationDate;
		this.executionTime = DEFAULT_EXECUTION_TIME_VALUE;
	}
	
	public Cursor(String path, Date creationDate, Date lastExecutionDate) {
		super();
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.creationDate = creationDate;
		this.lastExecutionDate = lastExecutionDate;
		this.executionTime = DEFAULT_EXECUTION_TIME_VALUE;
	}
	
	public Cursor(String path, Date creationDate, Date lastExecutionDate, long executionTimesInSeconds) {
		super();
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.creationDate = creationDate;
		this.lastExecutionDate = lastExecutionDate;
		this.executionTime = executionTimesInSeconds + "";
	}

	public Cursor(String id, String path, Date creationDate) {
		super();
		this.id = id;
		this.path = path;
		this.creationDate = creationDate;
		this.executionTime = DEFAULT_EXECUTION_TIME_VALUE;
	}
	
	public Cursor(String id, String path, Date creationDate, Date lastExecutionDate, long executionTimesInSeconds) {
		super();
		this.id = id;
		this.path = path;
		this.creationDate = creationDate;
		this.lastExecutionDate = lastExecutionDate;
		this.executionTime = String.valueOf(executionTimesInSeconds);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(Date lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		return "Cursor [id=" + id + ", path=" + path + ", creationDate=" + creationDate + ", lastExecutionDate=" + lastExecutionDate + ", executionTime=" + executionTime + "]";
	}

}
