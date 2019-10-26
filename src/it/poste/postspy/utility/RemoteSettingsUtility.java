package it.poste.postspy.utility;

import java.io.File;

import org.eclipse.core.runtime.Platform;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.entity.RemoteSettings;

public class RemoteSettingsUtility {
	
	public static File getRemoteSettingsFile() {
		File remoteSettingsFile = null;
		try {
//			String workspacePath = Platform.getLocation().toString();
//			remoteSettingsFile = new File(workspacePath + File.separator + ResourcesConstant.REMOTE_SETTINGS_FILE_NAME);
			PropertiesUtility propertiesUtility = new PropertiesUtility();
			String pathGeneratedCMD = propertiesUtility.getProperty(ResourcesConstant.PATH_GENERATED_CMD, ResourcesConstant.PROPERTIES_FILE_NAME);
			remoteSettingsFile = new File(pathGeneratedCMD + File.separator + ResourcesConstant.REMOTE_SETTINGS_FILE_NAME);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return remoteSettingsFile;
	}
	
	public static void saveRemoteSettingsFile(RemoteSettings remoteSettings) {
		try {
			XMLUtility.saveFileFromObject(remoteSettings, getRemoteSettingsFile().getAbsolutePath());
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	public static RemoteSettings getRemoteSettings() {
		RemoteSettings remoteSettings = null;
		try {
			if(getRemoteSettingsFile().exists()) {
				remoteSettings = (RemoteSettings) XMLUtility.getObjFromXMLFile(getRemoteSettingsFile(), RemoteSettings.class);
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return remoteSettings;
	}

}
