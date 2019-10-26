package it.poste.postspy.srutility;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.entity.Projects.Project;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.entity.SrConsole.Parameter;
import it.poste.postspy.thread.LocalScanThread;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ProjectsUtility;
import it.poste.postspy.utility.PropertiesUtility;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SRLocalAnalysis {
	
	private String pathsrConsole;
	private SrConsole srConsole;
	
	public SRLocalAnalysis(SrConsole srConsole) {
		this.srConsole = srConsole;
		inizializeProperties();
	}
	
	public void execute() {
		try {
			String parametersString = costruisciStringParametriSr();
			String lineCommand = generateSrLineCommand(pathsrConsole, parametersString);
			LocalScanThread processThread = new LocalScanThread(lineCommand, srConsole);
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(processThread);
			LogUtility.logInfo(MessageConstant.ANALYSIS_STARTED, null);
			saveProject();
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private String costruisciStringParametriSr() {
		String stringaParametri = "";
		String name, value;
		for(Parameter parameter:srConsole.getInputParameters()) {
			name = parameter.getName();
			value = parameter.getValue();
			stringaParametri += " " + name + 
					(value!=null && !"".equals(value)?"=" + "\"" + value + "\"":"");
		}
		LogUtility.logInfo("Stringa Parametri: " + stringaParametri, null);
		return stringaParametri;
	}
	
	private String generateSrLineCommand(String pathsrConsole, String parametri) {
		String lineCommand =  "cmd /c start /wait \"\" \"" + pathsrConsole + "\" ";
		lineCommand += parametri;
		return lineCommand;
	}
	
	private void inizializeProperties() {
		try {
			PropertiesUtility propertiesUtility = new PropertiesUtility();
			pathsrConsole = propertiesUtility.getProperty(ResourcesConstant.PATH_SR_CONSOLE, ResourcesConstant.PROPERTIES_FILE_NAME);
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void saveProject() {
		try {
			HashMap<String, String> parameters = getParametersListFromsrConsole(srConsole);
			String appName = parameters.get(SrConsoleConstant.APPLICATION_NAME);
			String appVersion = parameters.get(SrConsoleConstant.APPLICATION_VERSION);
			Project project = new Project();
			project.setName(appName);
			project.setVersion(appVersion);
			ProjectsUtility.addProject(project);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private HashMap<String, String> getParametersListFromsrConsole(SrConsole srConsole) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		try {
			for(Parameter parameter:srConsole.getInputParameters()) {
				parameters.put(parameter.getName(), parameter.getValue());
			}
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return parameters;			
	}

}
