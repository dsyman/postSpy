package it.poste.postspy.handler;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.srutility.SRRemoteAnalysis;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ProjectsUtility;
import it.poste.postspy.utility.PropertiesUtility;

import java.io.File;

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

public class DownloadAnalysisButtonHandler extends AbstractHandler {
	
	private String pathSrResults;
	
	private final String allResult = "All";
	
	public DownloadAnalysisButtonHandler() {
		inizializeProperties();
	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		try {
			String projectPath = "", projectName = "";
			boolean trovato = true;
			
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(arg0);
			
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
			
			if(trovato) {
				String md5 = ProjectsUtility.getProjectLastVersionMD5(projectName);
				SRRemoteAnalysis srRemoteAnalysis = new SRRemoteAnalysis(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage());
				srRemoteAnalysis.executeDownload(md5, allResult, pathSrResults + File.separator);
			}
			
			return null;
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
			return null;
		}
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathSrResults = propertiesUtility.getProperty(ResourcesConstant.PATH_SR_RESULTS, ResourcesConstant.PROPERTIES_FILE_NAME);
	}

}
