package it.poste.postspy.handler;

import it.poste.postspy.constant.FileSystemConstant;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.entity.SrConsole.Parameter;
import it.poste.postspy.srutility.SRRemoteAnalysis;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.PropertiesUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class AnalysisButtonHandler extends AbstractHandler {
	
	private String pathSRMultiScan, pathGeneratedCMD;

	public AnalysisButtonHandler() {
		inizializeProperties();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			String projectPath = "", projectName = "";
			boolean trovato = true;
//			
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//			
			if (window != null) {
				IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
				
				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof IAdaptable) {
					IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
					IPath path = project.getFullPath();
					projectName = path!=null?path.toFile().getName():"";
					projectPath = project.getLocation().toFile().getAbsolutePath();
				}
				
			}
			
			if(!new File(projectPath).exists()) {
				DirectoryDialog dlg = new DirectoryDialog(window.getShell());
				dlg.setText("Select Source Code Parent Directory");
				String dir = dlg.open();
				if (dir != null && new File(dir).getName().equals(projectName)) {
					projectPath = dir;
				} else {
					MessageDialog.openInformation(
							window.getShell(),
							"Message",
							"Wrong Root Folder");
					trovato = false;
				}
			}
//			
//			if(trovato) {			    
//				SRWizard srWizard = new SRWizard(window.getShell(), new File(projectPath).getAbsolutePath(), projectName);
//				srWizard.addPages();
//				SrWizardDialog srWizardDialog = new SrWizardDialog(window.getShell(), srWizard);
//				srWizardDialog.create();
//				srWizardDialog.open();
//			}
			
			if(trovato) {
				ProcessBuilder pb = new ProcessBuilder();
				pb.command(pathSRMultiScan, SrConsoleConstant.SR_SOURCE_PATH.replace(SrConsoleConstant.VALUE, projectPath), 
						SrConsoleConstant.SR_APPLICATION.replace(SrConsoleConstant.VALUE, projectName), SrConsoleConstant.SR_ECLIPSE_PLUGIN);
				Process process = pb.start();
				process.waitFor();
				// TODO aggiungere controllo se remoto, altrimenti non fare nulla
				SrConsole srConsole = getAllParameters(projectName);
				SRRemoteAnalysis srRemoteAnalysis = new SRRemoteAnalysis(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage());
				srRemoteAnalysis.executeScan(srConsole, "");
			}
	    	
			return null;
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
			return null;
		}
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathSRMultiScan = propertiesUtility.getProperty(ResourcesConstant.PATH_MULTI_SCAN, ResourcesConstant.PROPERTIES_FILE_NAME);
		pathGeneratedCMD = propertiesUtility.getProperty(ResourcesConstant.PATH_GENERATED_CMD, ResourcesConstant.PROPERTIES_FILE_NAME);
	}
	
	private SrConsole getAllParameters(String projectName) throws Exception {
		try {
			String parametersString = getParametersLineString(projectName);
			SrConsole srConsole = new SrConsole();
			String[] parameters = parametersString.split(" -");
			Parameter parameter;
			String name, value;
			for(int i=1;i<parameters.length;i++) {
				name = parameters[i].split("=")[0];
				if("custom".equals(name)) {
					continue;
				}
				value = parameters[i].split("=").length>1?parameters[i].split("=")[1]:"";
				parameter = new Parameter();
				parameter.setName("-" + name);
				parameter.setValue(!"".equals(value)?value.substring(1, value.length()-1):value);
//				LogUtility.logInfo("parameter name: " + parameter.getName(), null);
//				LogUtility.logInfo("parameter value: " + parameter.getValue(), null);
				srConsole.getInputParameters().add(parameter);
			}
			return srConsole;
		} catch(Exception exception) {
			throw exception;
		}
	}
	
	private String getParametersLineString(String projectName) throws Exception {
		try {
			String rowIdentifier = "\"C:\\Security Reviewer\\Client\\SRConsole.exe\"";
			String parametersString = null;
			String cmdFilePath = pathGeneratedCMD + File.separator + projectName + FileSystemConstant.CMD_EXTENSION;
			File cmdFile = new File(cmdFilePath);
			if(cmdFile.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(cmdFile));
				String riga = "";
				while( (riga=bufferedReader.readLine())!=null ) {
					parametersString = riga.startsWith(rowIdentifier)?riga.trim():parametersString;
				}
				bufferedReader.close();
			}
			return parametersString;
		} catch(Exception exception) {
			throw exception;
		}
	}
	
}
