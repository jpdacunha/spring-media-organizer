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
@ConfigurationProperties("media-batch")
@Getter @Setter
@ToString
public class MediaBatchYamlConfiguration {
	
	public Paths paths;

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

}
