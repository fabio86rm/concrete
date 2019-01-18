package com.main.java.concrete.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LettoreProperties {
	
	final static Logger logger = Logger.getLogger(LettoreProperties.class);

	public static void leggiPropertiesLog4j() {
		
		//PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure("conf/log4j.properties");
        
//		if(logger.isDebugEnabled()){
//			logger.debug("This is debug : ");
//		}
//		if(logger.isInfoEnabled()){
//			logger.info("This is info : ");
//		}
//		logger.warn("This is warn : ");
//		logger.error("This is error : ");
//		logger.fatal("This is fatal : ");
	}
	
	public static Properties leggiPropertiesConfigurazione() {
		Properties prop = new Properties();
		
		try {
		    prop.load(new FileInputStream("conf/config.properties"));
		} catch (IOException e) {
			logger.error("Errore nella lettura dei properties: "+e.getMessage());
		    e.printStackTrace();
		}
		
		return prop;
	}
	
}
