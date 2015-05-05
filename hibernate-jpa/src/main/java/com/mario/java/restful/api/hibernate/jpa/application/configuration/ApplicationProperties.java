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
	private final String fileName = System.getProperty("conf.dir")+"application.properties";
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
		this.properties = new Properties();
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			LOGGER.info("InputStream error:" + e.getMessage());
		}
		
		try {
			this.properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.info("load error:" + e.getMessage());
		}
	}
}