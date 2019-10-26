package it.poste.postspy.thread;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.utility.LogUtility;

public class LocalScanThread implements Runnable {
	
	private String lineCommand;
	
	public LocalScanThread(String lineCommand, SrConsole srConsole) {
		this.lineCommand = lineCommand;
	}

	@Override
	public void run() {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(lineCommand);
			pr.waitFor();
			int result = pr.exitValue();
			LogUtility.logInfo(MessageConstant.RESULT_CODE + result, null);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}

}
