package it.poste.postspy.importWizards;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.SecurityReviewerElement;
import it.poste.postspy.entity.SecurityReviewerElementParent;
import it.poste.postspy.utility.LogUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SecurityReviewerImportData {
	private static SecurityReviewerElementParent root;
	private static String pathDeadCode;
	private static String pathSecurity;
	private static String ruleset;
	private static String fileDeadCode;
	private static String fileSecurity;
	
	public static void importData(String deadcodeFileName, String securityFileName) {
		try {
			LogUtility.logInfo(MessageConstant.DEADCODE_FILE + deadcodeFileName, null);
			LogUtility.logInfo(MessageConstant.SECURITY_FILE + securityFileName, null);
			pathDeadCode=null;
			pathSecurity=null;
			fileDeadCode=null;
			fileSecurity=null;
//		SecurityReviewerImportData.path = path;
			root = new SecurityReviewerElementParent("", "", "", "" , "", "", "");
			SecurityReviewerElementParent deadcode = new SecurityReviewerElementParent("", "" , "", "", "", "", "");
			SecurityReviewerElementParent security = new SecurityReviewerElementParent("", "" , "", "", "", "", "");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			
			if(deadcodeFileName != null && deadcodeFileName != "") {
				try {
					DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
					InputStream deadcodeInputStream = new FileInputStream(new File(deadcodeFileName));
					Document deadcodeDocument = documentBuilder.parse(deadcodeInputStream);
					Element projectInformations = (Element) deadcodeDocument.getElementsByTagName("ProjectInformations").item(0);
					String processing=projectInformations.getAttribute("Processing");
					if(!processing.equals("DEADCODE")){
						deadcode.setElement("Deadcode (Wrong xml file ...)");
					}
					else{
						fileDeadCode= new File(deadcodeFileName).getAbsolutePath();
						ruleset = projectInformations.getAttribute("CWEOWASP");
						pathDeadCode=projectInformations.getAttribute("Tracking");
						NodeList deadcodeViolationNodeList = deadcodeDocument.getElementsByTagName("Violation");
						for (int contViolation = 0; contViolation < deadcodeViolationNodeList.getLength(); contViolation++) {
							SecurityReviewerElement securityReviewerElement = new SecurityReviewerElement();
							
							Element violationElement = (Element) deadcodeViolationNodeList.item(contViolation);
							securityReviewerElement.setElement(violationElement.getAttribute("Path"));
							securityReviewerElement.setSeverity(violationElement.getAttribute("Sev"));
							securityReviewerElement.setCwe(violationElement.getAttribute("Encoding") + " - " + violationElement.getAttribute("descencoding"));
							securityReviewerElement.setOwasp("-");
							securityReviewerElement.setCvss(violationElement.getAttribute("CVSS_Score"));
							securityReviewerElement.setLine(violationElement.getAttribute("ln"));
							securityReviewerElement.setDescription(violationElement.getAttribute("msg"));
							securityReviewerElement.setTip(violationElement.getAttribute("Snippet"));
							securityReviewerElement.setCategory(violationElement.getAttribute("cat"));
							
							deadcode.addChild(securityReviewerElement);
						}
						
						deadcode.setElement("Deadcode (" + deadcode.getChildren().length + ")");
					}
				} catch(Exception exception) {
					deadcode.setElement("Deadcode (Loading error ...)");
					LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
				}
				
				root.addChild(deadcode);
			}
			
			if(securityFileName != null && securityFileName != "") {
				try {
					DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
					InputStream securityInputStream = new FileInputStream(new File(securityFileName));
					Document securityDocument = documentBuilder.parse(securityInputStream);
					Element projectInformations = (Element) securityDocument.getElementsByTagName("ProjectInformations").item(0);
					String processing=projectInformations.getAttribute("Processing");
					if(!processing.equals("SECURITY")){
						deadcode.setElement("Security (Wrong xml file ...)");
					}
					else{	
						fileSecurity= new File(securityFileName).getAbsolutePath();
						ruleset = projectInformations.getAttribute("CWEOWASP");
						pathSecurity=projectInformations.getAttribute("Tracking");
						NodeList securityViolationNodeList = securityDocument.getElementsByTagName("Violation");
						for (int contViolation = 0; contViolation < securityViolationNodeList.getLength(); contViolation++) {
							SecurityReviewerElement securityReviewerElement = new SecurityReviewerElement();
							
							Element violationElement = (Element) securityViolationNodeList.item(contViolation);
							securityReviewerElement.setElement(violationElement.getAttribute("Path"));
							securityReviewerElement.setSeverity(violationElement.getAttribute("Sev"));
							
//					if(ruleset.equals("CWE")) {
//						securityReviewerElement.setCwe(violationElement.getAttribute("Encoding") + " - " + violationElement.getAttribute("descencoding"));
//						securityReviewerElement.setOwasp("-");
//					} else {
//						securityReviewerElement.setCwe("-");
//						securityReviewerElement.setOwasp(violationElement.getAttribute("Encoding") + " - " + violationElement.getAttribute("descencoding"));
//					}
							
							securityReviewerElement.setOwasp(violationElement.getAttribute("Encoding") + " - " + violationElement.getAttribute("descencoding"));
							securityReviewerElement.setCwe(violationElement.getAttribute("CWEOWASP"));
							
							securityReviewerElement.setCvss(violationElement.getAttribute("CVSS_Score"));
							securityReviewerElement.setLine(violationElement.getAttribute("ln"));
							securityReviewerElement.setDescription(violationElement.getAttribute("msg"));
							securityReviewerElement.setTip(violationElement.getAttribute("Snippet"));
							securityReviewerElement.setCategory(violationElement.getAttribute("cat"));
							
							security.addChild(securityReviewerElement);
						}
						
						security.setElement("Security (" + security.getChildren().length + ")");
					}
				} catch(Exception exception) {
					security.setElement("Security (Loading error ...)");
					LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
				}
				
				root.addChild(security);
			}
			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	public static SecurityReviewerElementParent getRoot() {
		return root;
	}
	
	public static String getDeadCodePath() {
		return pathDeadCode;
	}
	
	public static String getSecurityPath() {
		return pathSecurity;
	}
	
	public static void setDeadCodePath(String path) {
		pathDeadCode=path;
	}
	
	public static void setSecurityPath(String path) {
		pathSecurity=path;
	}
	
	public static String getFileSecurity(){
		return fileSecurity;
	}
	
	public static String getFileDeadCode(){
		return fileDeadCode;
	}
	
	public static String getRuleset() {
		return ruleset;
	}
}