package it.poste.postspy.thread;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.srcliclient.controller.StatusController;
import it.poste.srcliclient.entity.ResultObj;

import org.eclipse.ui.IWorkbenchPage;

public class RemoteStatusThread implements Runnable {
	
	private String url, ipAddress, macAddress, md5;
	
	private IWorkbenchPage iWorkbenchPage;
	
	public RemoteStatusThread(String url, String ipAddress, String macAddress, String md5, IWorkbenchPage iWorkbenchPage) {
		this.url = url;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.md5 = md5;
		this.iWorkbenchPage = iWorkbenchPage;
	}
	
	@Override
	public void run() {
		try {
			StatusController statusController = new StatusController(ipAddress, macAddress, md5);
			ResultObj resultObj = statusController.execute(url);
			if(resultObj.getErrorMessage()!=null) {
				LogUtility.logInfo("Error: " + resultObj.getErrorMessage(), null);
//				MessageDialog.openInformation(
//						shell,
//						"Message",
//						resultObj.getErrorMessage());
			} else {
				LogUtility.logInfo("Status: " + resultObj.getResult(), null);
//				MessageDialog.openInformation(
//						shell,
//						"Message",
//						"Status: " + resultObj.getResult());
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}

}
