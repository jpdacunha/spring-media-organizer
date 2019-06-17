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

	}

}
