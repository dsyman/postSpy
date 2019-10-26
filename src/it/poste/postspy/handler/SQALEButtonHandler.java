package it.poste.postspy.handler;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.shell.SqaleShell;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.PropertiesUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SQALEButtonHandler extends AbstractHandler{
	
	private String pathSQALEConsole, lastOpenFilePath, pathSrResults;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			inizializeProperties();
			String projectName = getProjectName(event);
			SqaleShell sqaleShell = new SqaleShell(projectName, this, pathSrResults);
			sqaleShell.show();	
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return null;
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathSQALEConsole = propertiesUtility.getProperty(ResourcesConstant.PATH_SQALE_CONSOLE, ResourcesConstant.PROPERTIES_FILE_NAME);
		lastOpenFilePath = propertiesUtility.getProperty(ResourcesConstant.PATH_LAST_OPEN_FILE, ResourcesConstant.PROPERTIES_FILE_NAME);
		pathSrResults = propertiesUtility.getProperty(ResourcesConstant.PATH_SR_RESULTS, ResourcesConstant.PROPERTIES_FILE_NAME);
	}
	
	public void executeSqale(String getResultFile) {
		try{
//			createLastOpenFile(getResultFile);
			String initialParameter = SrConsoleConstant.INITIAL_PARAMETERS.replace(SrConsoleConstant.VALUE, getResultFile);
			String noSrcParameter = SrConsoleConstant.NO_SRC;
	        String command = "cmd /c start /wait \"\" " + "\"" + pathSQALEConsole + "\"";
	        String parametri = " " + initialParameter + " " + noSrcParameter + " " + SrConsoleConstant.PLUGIN_JAVA;
	        command += parametri;
	        Process child = Runtime.getRuntime().exec(command);
		} catch(IOException e){
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private String getProjectName(ExecutionEvent event) {
		String projectName = null;
		try {
			IWorkbenchWindow checkedWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			if (checkedWindow!=null) {
		        IStructuredSelection selection = (IStructuredSelection) checkedWindow.getSelectionService().getSelection();
		        Object firstElement = selection.getFirstElement();
		        if (firstElement instanceof IAdaptable) {
		            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
		            IPath path = project.getFullPath();
		            projectName = path!=null?path.toFile().getName():null;
		        }
			}
		} catch (ExecutionException e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return projectName;
	}
	
	private boolean createLastOpenFile(String getResultFile) {
		boolean ok = true;
		File lastOpenFile = null;
		try {
			lastOpenFile = new File(lastOpenFilePath);
			lastOpenFile.getParentFile().mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(lastOpenFile));
			bufferedWriter.write(getResultFile);
			bufferedWriter.close();
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
			ok = false;
		}
		return ok;
	}

}
