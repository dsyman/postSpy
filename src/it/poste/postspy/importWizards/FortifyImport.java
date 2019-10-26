package it.poste.postspy.importWizards;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.FortifyElement;
import it.poste.postspy.entity.FortifyElementParent;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.views.FortifyView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FortifyImport extends Wizard implements IImportWizard {
	public static final String ID = "it.poste.postspy.importWizards.FortifyImport";
	
	public FortifyImport() {
		super();
		setWindowTitle("File Import Wizard");
    	setNeedsProgressMonitor(true);
	}
	
	private FortifyImportPage fortifyImportPage;
	private static FortifyElementParent root;
	private static String path;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public boolean performFinish() {
		try {
//			path = fortifyImportPage.getPath();
			
			root = new FortifyElementParent("", "", "", "" , "", "", "");
			final FortifyElementParent deadcode = new FortifyElementParent("", "" , "", "", "", "", "");
			final FortifyElementParent security = new FortifyElementParent("", "" , "", "", "", "", "");
			final String fileName = fortifyImportPage.getFileName();
			LogUtility.logInfo(MessageConstant.FORTIFY_FILE + fileName, null);
			
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)  {
					try {
						monitor.setTaskName("Loading file ...");
						DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
						InputStream inputStream = new FileInputStream(new File(fileName));
						Document document = documentBuilder.parse(inputStream);
						
						path= document.getElementsByTagName("SourceBasePath").item(0).getTextContent();
						monitor.setTaskName("Loading description ...");
						HashMap<String, String> descriptionHashMap = new HashMap<String, String>();
						
						NodeList descriptionNodeList = document.getElementsByTagName("Description");
						
						for (int contDescription = 0; contDescription < descriptionNodeList.getLength(); contDescription++) {
							Element descriptionElement = (Element) descriptionNodeList.item(contDescription);
							Element abstractElement = (Element) descriptionElement.getElementsByTagName("Abstract").item(0);
							String description = abstractElement.getTextContent();
							description = description.replaceAll("<Content>", "");
							description = description.replaceAll("</Content>", "");
							description = description.replaceAll("<Paragraph>", "");
							description = description.replaceAll("</Paragraph>", "");
							description = description.replaceAll("<AltParagraph>", "");
							description = description.replaceAll("</AltParagraph>", "");
							description = description.replaceAll("<code>", "");
							description = description.replaceAll("</code>", "");
							
							descriptionHashMap.put(descriptionElement.getAttribute("classID"), description);
							
						}
						
						monitor.setTaskName("Loading Tips ...");
						HashMap<String, String> tipHashMap = new HashMap<String, String>();
						descriptionNodeList = document.getElementsByTagName("Description");
						for (int contDescription = 0; contDescription < descriptionNodeList.getLength(); contDescription++) {
							Element descriptionElement = (Element) descriptionNodeList.item(contDescription);
							if(descriptionElement.getElementsByTagName("Recommendations")!=null && descriptionElement.getElementsByTagName("Recommendations").getLength()!=0){
							Element tipElement = (Element) descriptionElement.getElementsByTagName("Recommendations").item(0);
							String tip = tipElement.getTextContent();
							tip = tip.replaceAll("<Content>", "");
							tip = tip.replaceAll("</Content>", "");
							tip = tip.replaceAll("<Paragraph>", "");
							tip = tip.replaceAll("</Paragraph>", "");
							tip = tip.replaceAll("<AltParagraph>", "");
							tip = tip.replaceAll("</AltParagraph>", "");
							tip = tip.replaceAll("<code>", "");
							tip = tip.replaceAll("</code>", "");
							tipHashMap.put(descriptionElement.getAttribute("classID"), tip);
							
							}
						}
						
						
						monitor.setTaskName("Loading CWE - OWASP ...");
						HashMap<String, String> cweHashMap = new HashMap<String, String>();
						HashMap<String, String> owaspHashMap = new HashMap<String, String>();
						Element engineDataElement = (Element) document.getElementsByTagName("EngineData").item(0);
						NodeList ruleNodeList = engineDataElement.getElementsByTagName("Rule");
						
						for (int contRule = 0; contRule < ruleNodeList.getLength(); contRule++) {
							Element ruleElement = (Element) ruleNodeList.item(contRule);
							NodeList groupNodeList = ruleElement.getElementsByTagName("Group");
							for (int contGroup = 0; contGroup < groupNodeList.getLength(); contGroup++) {
								Element groupElement = (Element) groupNodeList.item(contGroup);
								
								if(groupElement.getAttribute("name").equals("altcategoryCWE")) {
									cweHashMap.put(ruleElement.getAttribute("id"), groupElement.getTextContent());
								}
								
								if(groupElement.getAttribute("name").equals("altcategoryOWASP2010")) {
									owaspHashMap.put(ruleElement.getAttribute("id"), groupElement.getTextContent());
								}
							}
						}
						
						NodeList vulnerabilityNodeList = document.getElementsByTagName("Vulnerability");
						monitor.beginTask("", vulnerabilityNodeList.getLength());
						
						for (int contVulnerability = 0; contVulnerability < vulnerabilityNodeList.getLength(); contVulnerability++) {
							monitor.subTask("Loading vulnerability ... (" + contVulnerability + " of " + vulnerabilityNodeList.getLength() + ")");
							FortifyElement fortifyElement = new FortifyElement();
							
							Element vulnerabilityElement = (Element) vulnerabilityNodeList.item(contVulnerability);
							Element classInfoElement = (Element) vulnerabilityElement.getElementsByTagName("ClassInfo").item(0);
							Element classIDElement = (Element) classInfoElement.getElementsByTagName("ClassID").item(0);
							String classID = classIDElement.getTextContent();
							fortifyElement.setOwasp(owaspHashMap.get(classID));
							fortifyElement.setCwe(cweHashMap.get(classID));
						
							Element typeElement = (Element) classInfoElement.getElementsByTagName("Type").item(0);
							String type = typeElement.getTextContent();
							
							Element subtypeElement = (Element) classInfoElement.getElementsByTagName("Subtype").item(0);
							if(subtypeElement != null) {
								fortifyElement.setCategory(type + " - " + subtypeElement.getTextContent());
							} else {
								fortifyElement.setCategory(type);
							}
							
							Element defaultSeverityElement = (Element) classInfoElement.getElementsByTagName("DefaultSeverity").item(0);
							fortifyElement.setSeverity(defaultSeverityElement.getTextContent());
							
						
							Element analysisInfoElement = (Element) vulnerabilityElement.getElementsByTagName("AnalysisInfo").item(0);
							Element replacementDefinitionsElement = (Element) analysisInfoElement.getElementsByTagName("ReplacementDefinitions").item(0);
							String description = descriptionHashMap.get((classID));
														
							if(replacementDefinitionsElement != null) {
								NodeList defNodeList = replacementDefinitionsElement.getElementsByTagName("Def");
								
								for (int contDef = 0; contDef < defNodeList.getLength(); contDef++) {
									Element defElement = (Element) defNodeList.item(contDef);
									description = description.replaceAll("[$]", "S");
									description = description.replaceAll("<Replace key=\"" + defElement.getAttribute("key").replaceAll("[$]", "S") + "\"/>", defElement.getAttribute("value"));
								}
							}
							fortifyElement.setDescription(description);
							if(tipHashMap.containsKey(classID)){
							String tips = tipHashMap.get((classID));
							
							
							if(replacementDefinitionsElement != null) {
								NodeList defNodeList = replacementDefinitionsElement.getElementsByTagName("Def");
								
								for (int contDef = 0; contDef < defNodeList.getLength(); contDef++) {
									Element defElement = (Element) defNodeList.item(contDef);
									tips = tips.replaceAll("[$]", "S");
									tips = tips.replaceAll("<Replace key=\"" + defElement.getAttribute("key").replaceAll("[$]", "S") + "\"/>", defElement.getAttribute("value"));
								}
							}
							
							fortifyElement.setTip(tips);
							}
							else
								fortifyElement.setTip("");
							
							Element unifiedElement = (Element) analysisInfoElement.getElementsByTagName("Unified").item(0);
							Element traceElement = (Element) unifiedElement.getElementsByTagName("Trace").item(0);
							Element primaryElement = (Element) traceElement.getElementsByTagName("Primary").item(0);
							NodeList entryNodeList = primaryElement.getElementsByTagName("Entry");
							
							for (int contEntry = 0; contEntry < entryNodeList.getLength(); contEntry++) {
								Element entryElement = (Element) entryNodeList.item(contEntry);
								Element nodeElement = (Element) entryElement.getElementsByTagName("Node").item(0);
								
								if(nodeElement != null && nodeElement.getAttribute("isDefault").equals("true")) {
									Element sourceLocationElement = (Element) entryElement.getElementsByTagName("SourceLocation").item(0);
									fortifyElement.setElement(sourceLocationElement.getAttribute("path"));
									fortifyElement.setLine(sourceLocationElement.getAttribute("line"));
								}
							}
							
							
							if(type.equals("Dead Code")) {
								deadcode.addChild(fortifyElement);
							} else {
								security.addChild(fortifyElement);
							}
							
							monitor.worked(1);
						}
						
						deadcode.setElement("Deadcode (" + deadcode.getChildren().length + ")");
						security.setElement("Security (" + security.getChildren().length + ")");
												
						monitor.done();
					} catch(Exception exception) {
//						String res=null;
//						StackTraceElement[] elements = exception.getStackTrace();
//						for(int i=0;i<elements.length;i++){
//							res+=elements[i];
//						}
//						MessageDialog.openInformation(getShell(), "Warning",res);
						deadcode.setElement("Deadcode (Loading error ...)");
						security.setElement("Security (Loading error ...)");
						LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
					}
				}
			});
			
			root.addChild(deadcode);
			root.addChild(security);
			
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(FortifyView.ID);
		
			FortifyView.refresh();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		
		return true;
	}
	
	public void addPages() {
		try {
			fortifyImportPage = new FortifyImportPage("Import Fortify file");
			addPage(fortifyImportPage);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
    }
	
	public static FortifyElementParent getRoot() {
		return root;
	}
	
	public static String getPath() {
		return path; 
	}
	
	public static void setPath(String p) {
		path=p; 
	}
}