package it.poste.postspy.utility;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtility {
	
	public String getProperty(String propertyName, String propertyFileName) {
		try {
			InputStream inputStreamProperties = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			Properties properties = new Properties();
        	properties.load(inputStreamProperties);
        	String propertyValue = properties.getProperty(propertyName);
        	return propertyValue;
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
			return null;
		}
	}
	
	public void saveProxyProperty(Properties properties) throws Exception {
		try {
			String proxyPropertiesPath = getProperty(ResourcesConstant.PATH_SR_CONSOLE, ResourcesConstant.PROXY_PROPERTIES_FILE_NAME);
        	File propertiesFile = new File(proxyPropertiesPath);
        	BufferedWriter outputStreamProperties = new BufferedWriter(new FileWriter(propertiesFile));
        	properties.store(outputStreamProperties, null);
        	outputStreamProperties.close();
		} catch(Exception e) {
			throw e;
		}
	}
	
	public static Properties getProxyProperties() throws Exception {
		try {
			PropertiesUtility propertiesUtility = new PropertiesUtility();
			String proxyPropertiesPath = propertiesUtility.getProperty(ResourcesConstant.PATH_SR_CONSOLE, ResourcesConstant.PROXY_PROPERTIES_FILE_NAME);
			Properties properties = new Properties();
			FileReader fileReader = new FileReader(new File(proxyPropertiesPath)); 
        	properties.load(fileReader);
        	fileReader.close();
        	return properties;
		} catch(FileNotFoundException fnfe) {
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

}