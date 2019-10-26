package it.poste.postspy.handler;

import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.utility.PropertiesUtility;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RulesDButtonHandler extends AbstractHandler {
	
	String pathRulesD;
	
	public RulesDButtonHandler() {
		inizializeProperties();
	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(pathRulesD, SrConsoleConstant.PLUGIN_JAVA);
	    	pb.start();
		} catch(Exception exception) {
			
		}
		return null;
	}
	
	private void inizializeProperties() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		pathRulesD = propertiesUtility.getProperty(ResourcesConstant.PATH_RULES_D, ResourcesConstant.PROPERTIES_FILE_NAME);
	}

}
