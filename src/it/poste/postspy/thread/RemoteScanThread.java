package it.poste.postspy.thread;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.entity.Projects;
import it.poste.postspy.entity.Projects.Project;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.entity.SrConsole.Parameter;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ProjectsUtility;
import it.poste.srcliclient.controller.ScanController;
import it.poste.srcliclient.entity.ResultObj;
import it.poste.srcliclient.entity.SrScanInfo;
import it.poste.srcliclient.entity.SrScanInfo.InputParameters;

import java.util.HashMap;


public class RemoteScanThread implements Runnable {
	
	private SrConsole srConsole;
	private String url, ipAddress, macAddress, delta;
	
	public RemoteScanThread(String url, SrConsole srConsole, String ipAddress, String macAddress, String delta) {
		this.url = url;
		this.srConsole = srConsole;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.delta = delta;
	}
	
	@Override
	public void run() {
		try {
			SrScanInfo srScanInfo = getSrScanInfo();
			ScanController scanController = new ScanController(srScanInfo, ipAddress, macAddress, delta);
			ResultObj resultObj = scanController.execute(url);
			if(resultObj.getErrorMessage()!=null) {
				LogUtility.logInfo("Error: " + resultObj.getErrorMessage(), null);
			} else {
				LogUtility.logInfo("MD5: " + resultObj.getResult(), null);
				saveMD5(resultObj.getResult());
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private SrScanInfo getSrScanInfo() {
		SrScanInfo srScanInfo = new SrScanInfo();
		try {
			InputParameters inputParameters = new InputParameters();
			for(Parameter ele:srConsole.getInputParameters()) {
				it.poste.srcliclient.entity.SrScanInfo.InputParameters.Parameter parameter = 
						new it.poste.srcliclient.entity.SrScanInfo.InputParameters.Parameter();
				parameter.setName(ele.getName());
				parameter.setValue(ele.getValue());
				inputParameters.getParameter().add(parameter);
			}
			srScanInfo.setInputParameters(inputParameters);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return srScanInfo;
	}
	
	private void saveMD5(String md5) {
		try {
			HashMap<String, String> parameters = getParametersListFromSrConsole(srConsole);
			String appName = parameters.get(SrConsoleConstant.APPLICATION_NAME);
			String appVersion = parameters.get(SrConsoleConstant.APPLICATION_VERSION);
			Projects projects = ProjectsUtility.getProjects();
			for(Project project:projects.getProject()) {
				if(appName.equals(project.getName()) && appVersion.equals(project.getVersion())) {
					project.setMd5(md5);
					break;
				}
			}
			ProjectsUtility.saveProjectsFile(projects);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private HashMap<String, String> getParametersListFromSrConsole(SrConsole srConsole) {
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
