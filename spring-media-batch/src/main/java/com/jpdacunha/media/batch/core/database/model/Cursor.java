package com.jpdacunha.media.batch.core.database.model;

import java.util.Date;
import java.util.UUID;

public class Cursor {
	
	private String id;
	private String path;
	private Date creationDate;
	private Date lastExecutionDate;

	public Cursor(String path, Date creationDate) {
		super();
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.creationDate = creationDate;
	}

	public Cursor(String id, String path, Date creationDate) {
		super();
		this.id = id;
		this.path = path;
		this.creationDate = creationDate;
	}
	
	public Cursor(String id, String path, Date creationDate, Date lastExecutionDate) {
		super();
		this.id = id;
		this.path = path;
		this.creationDate = creationDate;
		this.lastExecutionDate = lastExecutionDate;
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

	@Override
	public String toString() {
		return "Cursor [id=" + id + ", path=" + path + ", creationDate=" + creationDate + ", lastExecutionDate=" + lastExecutionDate + "]";
	}

}
