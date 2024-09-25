package com.jpdacunha.media.batch.organizer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("media-batch-organizer")
@Getter @Setter
@ToString
public class MediaBatchYamlConfiguration {
	
	public Paths paths;
	private FileNamePatterns fileNamePatterns; 
	
	@Data
	public static class FileNamePatterns {
		
		private String[] photoFileNamePatterns;
		private String[] photoDatePatterns;
		
		public String[] getPhotoFileNamePatterns() {
			return photoFileNamePatterns;
		}
		public void setPhotoFileNamePatterns(String[] photoFileNamePatterns) {
			this.photoFileNamePatterns = photoFileNamePatterns;
		}
		public String[] getPhotoDatePatterns() {
			return photoDatePatterns;
		}
		public void setPhotoDatePatterns(String[] photoDatePatterns) {
			this.photoDatePatterns = photoDatePatterns;
		}

	}

	@Data
	public static class Paths {
		
		private String destinationRootDirPhoto;
		private String destinationRootDirVideo;
		private String[] startRootDirs;
		
		public String getDestinationRootDirPhoto() {
			return destinationRootDirPhoto;
		}
		public void setDestinationRootDirPhoto(String destinationRootDirPhoto) {
			this.destinationRootDirPhoto = destinationRootDirPhoto;
		}
		public String getDestinationRootDirVideo() {
			return destinationRootDirVideo;
		}
		public void setDestinationRootDirVideo(String destinationRootDirVideo) {
			this.destinationRootDirVideo = destinationRootDirVideo;
		}
		public String[] getStartRootDirs() {
			return startRootDirs;
		}
		public void setStartRootDirs(String[] startRootDirs) {
			this.startRootDirs = startRootDirs;
		}
		
	}

	public Paths getPaths() {
		return paths;
	}
	
	public void setPaths(Paths paths) {
		this.paths = paths;
	}

	public FileNamePatterns getFileNamePatterns() {
		return fileNamePatterns;
	}

	public void setFileNamePatterns(FileNamePatterns fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}

}
