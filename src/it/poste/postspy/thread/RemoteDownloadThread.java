package it.poste.postspy.thread;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ZipUtility;
import it.poste.srcliclient.controller.DownloadController;
import it.poste.srcliclient.entity.ResultObj;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;

public class RemoteDownloadThread implements Runnable {
	
	private String url, ipAddress, macAddress, md5, analysisType, destinationFolder;
	
	private IWorkbenchPage iWorkbenchPage;
	
	public RemoteDownloadThread(String url, String ipAddress, String macAddress, String md5, String analysisType, String destinationFolder, IWorkbenchPage iWorkbenchPage) {
		this.url = url;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.md5 = md5;
		this.analysisType = analysisType;
		this.destinationFolder = destinationFolder;
		this.iWorkbenchPage = iWorkbenchPage;
	}
	
	@Override
	public void run() {
		try {
			DownloadController downloadController = new DownloadController(ipAddress, macAddress, md5, analysisType, null,
					destinationFolder);
			ResultObj resultObj = downloadController.execute(url);
			if(resultObj.getErrorMessage()!=null) {
				LogUtility.logInfo("Error: " + resultObj.getErrorMessage(), null);
//				MessageDialog.openInformation(
//						Display.getDefault().getActiveShell(),
//						"Message",
//						resultObj.getErrorMessage());
			} else {
				String receivedFilePath =  resultObj.getResult();
				File unzippedFile = ZipUtility.unzip(new File(receivedFilePath).getParent(), receivedFilePath);
				new File(receivedFilePath).delete();
				LogUtility.logInfo("Received File: " + unzippedFile.getAbsolutePath(), null);
//				MessageDialog.openInformation(
//						Display.getDefault().getActiveShell(),
//						"Message",
//						"Received File: " + unzippedFile.getAbsolutePath());
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}

}
