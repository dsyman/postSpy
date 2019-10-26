package it.poste.postspy.handler;

import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.utility.PropertiesUtility;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ParametersButtonHandler extends AbstractHandler {
	
	String pathParameters;
	
	public ParametersButtonHandler() {
		inizializeProperties();
	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(pathParameters, SrConsoleConstant.PLUGIN_JAVA);
	    	pb.start();
		} catch(Exception exception) {
			
		}
		return null;
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathParameters = propertiesUtility.getProperty(ResourcesConstant.PATH_PARAMETERS, ResourcesConstant.PROPERTIES_FILE_NAME);
	}

}
