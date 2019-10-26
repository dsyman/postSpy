package it.poste.postspy.utility;

import it.poste.postspy.Activator;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;

public class LogUtility {
	
	public static ILog getActivatorLog() {
		Activator activator = Activator.getDefault();
		return activator.getLog();
	}
	
	public static void logError(String message, Exception e) {
		getActivatorLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, Status.ERROR, message, e));
	}
	
	public static void logInfo(String message, Exception e) {
		getActivatorLog().log(new Status(Status.INFO, Activator.PLUGIN_ID, Status.INFO, message, e));
	}

}
