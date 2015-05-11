package com.mario.java.restful.api.hibernate.jpa.application.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationProperties {
	
	private static final Logger LOGGER = Logger.getLogger(ApplicationProperties.class.getSimpleName());
	private static ApplicationProperties instance;
	private final String fileName = System.getProperty("conf.dir")+System.getProperty("properties.file.name");
	private Properties properties;
	
	protected ApplicationProperties() {
		this.setup();
	}
	
	public static ApplicationProperties getInstance() {
		if(ApplicationProperties.instance == null) {
			ApplicationProperties.instance = new ApplicationProperties();
		}
		
		return ApplicationProperties.instance;
	}
	
	public String getProperty(String name) {
		return this.properties.getProperty(name, null);
	}
	
	private void setup() {
		LOGGER.info("setup()");
		
		this.properties = new Properties();
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(this.fileName);
			this.properties.load(inputStream);
		} catch (FileNotFoundException e) {
			LOGGER.info("Could not load file {file}. Error: {error}".replace("{file}", fileName).replace("{error}", e.getMessage()));
		} catch (IOException e) {
			LOGGER.info("Could not load properties. Error: {error}".replace("{error}", e.getMessage()));
		}
	}
}