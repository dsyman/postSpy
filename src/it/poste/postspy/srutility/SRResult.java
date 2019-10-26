package it.poste.postspy.srutility;

import it.poste.postspy.constant.FileSystemConstant;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.PropertiesUtility;

import java.io.File;

public class SRResult {
	
	public static final String SECURITY_ANALYSIS = "Security";
	public static final String DEADCODE_ANALYSIS = "Deadcode";
	public static final String QUALITY_ANALYSIS = "Quality";
	
	private String pathSrResults;
	private String appName, appVersion;
	
	public SRResult(String appName, String appVersion) {
		inizializeProperties();
		this.appName = appName;
		this.appVersion = appVersion;
	}
	
	public String getXMLResult(String analysisType) {
		String analysisFilePath = null;
		try {
			String path = pathSrResults + File.separator + appName + File.separator + appVersion + File.separator + analysisType +
					File.separator + FileSystemConstant.INTERMEDIATE_PATH;
			File[] componentsFile = new File(path).listFiles();
			File componentFolder = null;
			for(File file:componentsFile) {
				if(file.getName().startsWith(FileSystemConstant.BEGIN_PATH_FIST_ELEMENT)) {
					componentFolder = file;
				}
			}
			analysisFilePath = componentFolder!=null?searchAnalysisFile(componentFolder):null;			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return analysisFilePath;
	}
	
	private String searchAnalysisFile(File componentFolder) {
		File analysisFile = null;
		try {
			File[] analysisFiles = componentFolder.listFiles();
			String fileName = appName + FileSystemConstant.XML_EXTENSION;
			for(File file:analysisFiles) {
				if(fileName.equals(file.getName())) {
					analysisFile = file;
				}
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return analysisFile!=null?analysisFile.getAbsolutePath():null;
	}
	
	private void inizializeProperties() {
		try {
			PropertiesUtility propertiesUtility = new PropertiesUtility();
			pathSrResults = propertiesUtility.getProperty(ResourcesConstant.PATH_SR_RESULTS, ResourcesConstant.PROPERTIES_FILE_NAME);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}

}