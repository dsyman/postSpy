package it.poste.postspy.srutility;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.entity.Projects;
import it.poste.postspy.entity.Projects.Project;
import it.poste.postspy.entity.RemoteSettings;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.entity.SrConsole.Parameter;
import it.poste.postspy.thread.RemoteDownloadThread;
import it.poste.postspy.thread.RemoteScanThread;
import it.poste.postspy.thread.RemoteStatusThread;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ProjectsUtility;
import it.poste.postspy.utility.ProxyUtil;
import it.poste.postspy.utility.RemoteSettingsUtility;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.ui.IWorkbenchPage;

public class SRRemoteAnalysis {
	
	@Inject UISynchronize sync;
	
	private String servicesBasePath, ipAddress, macAddress;
	
	private IWorkbenchPage iWorkbenchPage;

	public SRRemoteAnalysis(IWorkbenchPage iWorkbenchPage) {
		this.iWorkbenchPage = iWorkbenchPage;
		inizializeProperties();
	}
	
	public void executeScan(SrConsole srConsole, String delta) {
		try {
//			ProxyUtil.setSystemProxySettings();
			saveProject(srConsole);
			String url = servicesBasePath + ResourcesConstant.SCAN_RELATIVE_PATH;
			RemoteScanThread remoteScanThread = new RemoteScanThread(url, srConsole, ipAddress, macAddress, delta);
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(remoteScanThread);
			LogUtility.logInfo(MessageConstant.ANALYSIS_STARTED, null);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	public void executeStatus(String md5) {
		try {
//			ProxyUtil.setSystemProxySettings();
			String url = servicesBasePath + ResourcesConstant.STATUS_RELATIVE_PATH;
			RemoteStatusThread remoteStatusThread = new RemoteStatusThread(url, ipAddress, macAddress, md5, iWorkbenchPage);
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(remoteStatusThread);
			LogUtility.logInfo(MessageConstant.STATUS_STARTED, null);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	public void executeDownload(String md5, String analysisType, String destinationFolder) {
		try {
//			ProxyUtil.setSystemProxySettings();
			String url = servicesBasePath + ResourcesConstant.DOWNLOAD_RELATIVE_PATH;
			RemoteDownloadThread remoteDownloadThread = new RemoteDownloadThread(url, ipAddress, macAddress, md5, analysisType, destinationFolder, iWorkbenchPage);
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(remoteDownloadThread);
			LogUtility.logInfo(MessageConstant.DOWNLOAD_STARTED, null);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private void inizializeProperties() {
		try {
			RemoteSettings remoteSettings = RemoteSettingsUtility.getRemoteSettings();
			servicesBasePath = remoteSettings.getServicesBasePath();
			ipAddress = remoteSettings.getLocalIpAddress();
			macAddress = remoteSettings.getLocalMacAddress();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void saveProject(SrConsole srConsole) {
		try {
			HashMap<String, String> parameters = getParametersListFromsrConsole(srConsole);
			String appName = parameters.get(SrConsoleConstant.APPLICATION_NAME);
			String appVersion = parameters.get(SrConsoleConstant.APPLICATION_VERSION);
			
			boolean trovato = false;
			Projects projects = ProjectsUtility.getProjects();
			for(Project project:projects.getProject()) {
				if(appName.equalsIgnoreCase(project.getName()) && appVersion.equalsIgnoreCase(project.getVersion())) {
					trovato = true;
					break;
				}
			}
			
			if(!trovato) {
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				Date date = new Date();
				gregorianCalendar.setTime(date);
				XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
				Project project = new Project();
				project.setName(appName);
				project.setVersion(appVersion);
				project.setDate(xmlGregorianCalendar);
				ProjectsUtility.addProject(project);
			}
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
