package com.jpdacunha.media.batch.removeduplicatesfotos.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("media-batch-remove-duplicates-fotos")
@Getter @Setter
@ToString
public class RemoveDuplicatesFotosYamlConfiguration {
	
	public Paths paths;

	@Data
	public static class Paths {
		
		private String[] startRootDirs;
		
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
