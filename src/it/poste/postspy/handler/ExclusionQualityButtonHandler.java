package it.poste.postspy.handler;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.PropertiesUtility;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ExclusionQualityButtonHandler extends AbstractHandler {
	
	String pathExclusionQuality;
	
	public ExclusionQualityButtonHandler() {
		inizializeProperties();
	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(pathExclusionQuality, SrConsoleConstant.PLUGIN_JAVA);
	    	pb.start();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		return null;
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathExclusionQuality = propertiesUtility.getProperty(ResourcesConstant.PATH_EXCLUSION_QUALITY, ResourcesConstant.PROPERTIES_FILE_NAME);
	}

}
