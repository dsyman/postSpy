package it.poste.postspy.views;

import it.poste.postspy.Activator;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.SecurityReviewerElement;
import it.poste.postspy.entity.SecurityReviewerElementParent;
import it.poste.postspy.importWizards.SecurityReviewerImport;
import it.poste.postspy.importWizards.SecurityReviewerImportData;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.ZipUtility;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;


public class SecurityReviewerView extends ViewPart {
	public static final String ID = "it.poste.postspy.views.SecurityReviewerView";
	
	private static TreeViewer treeViewer;
	private Action selectPathAction;
	private Action zipResultAction;
	private Action doubleClickAction;
	private Action importSecurityReviewerAction;
	private Action importAction;
	private Action severity1Action;
	private Action severity2Action;
	private Action severity3Action;
	private Action severity4Action;
	private Action severity5Action;
	private boolean severity1 = false;
	private boolean severity2 = false;
	private boolean severity3 = false;
	private boolean severity4 = false;
	private boolean severity5 = false;
	
	public void createPartControl(Composite parent) {
		try {
			Tree tree = new Tree(parent, SWT.FULL_SELECTION);
			tree.setHeaderVisible(true);
			tree.setLinesVisible(true);
			treeViewer = new TreeViewer(tree);
			SecurityReviewerViewSorter sorter = new SecurityReviewerViewSorter();
			TreeViewerColumn element = new TreeViewerColumn(treeViewer, SWT.LEFT);
			element.getColumn().setText("Element");
			element.getColumn().setWidth(500);
			element.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					cell.setText(((SecurityReviewerElement) cell.getElement()).getElement());
					
					if (cell.getElement() instanceof SecurityReviewerElementParent) {
						cell.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER));
					} else {
						cell.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
					}
				}
			});
			sorter.addColumn(element, 1);
			
			TreeViewerColumn severity = new TreeViewerColumn(treeViewer, SWT.RIGHT);
			severity.getColumn().setText("Severity");
			severity.getColumn().setWidth(60);
			severity.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						String severity = ((SecurityReviewerElement) cell.getElement()).getSeverity();
						cell.setText(severity);
						
						if(severity.equals("1")) {
							Color color = new Color(Display.getCurrent(), 255, 0, 0);
							cell.setBackground(color);
						} else if(severity.equals("2")) {
							Color color = new Color(Display.getCurrent(), 250, 150, 0);
							cell.setBackground(color);
						} else if(severity.equals("3")) {
							Color color = new Color(Display.getCurrent(), 255, 255, 0);
							cell.setBackground(color);
						} else if(severity.equals("4")) {
							Color color = new Color(Display.getCurrent(), 0, 255, 0);
							cell.setBackground(color);
						} else if(severity.equals("5")) {
							Color color = new Color(Display.getCurrent(), 50, 150, 200);
							cell.setBackground(color);
						}
					}
				}
			});
			sorter.addColumn(severity, 2);
			
			TreeViewerColumn line = new TreeViewerColumn(treeViewer, SWT.RIGHT);
			line.getColumn().setText("Line");
			line.getColumn().setWidth(50);
			line.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getLine());
					}
				}
			});
			sorter.addColumn(line, 3);
			
			TreeViewerColumn description = new TreeViewerColumn(treeViewer, SWT.LEFT);
			description.getColumn().setText("Description");
			description.getColumn().setWidth(500);
			description.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getDescription());
					}
				}
			});
			sorter.addColumn(description, 4);
			
			TreeViewerColumn tip = new TreeViewerColumn(treeViewer, SWT.LEFT);
			tip.getColumn().setText("Tip");
			tip.getColumn().setWidth(500);
			tip.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getTip());
					}
				}
			});
			sorter.addColumn(tip, 5);
			
			TreeViewerColumn cwe = new TreeViewerColumn(treeViewer, SWT.LEFT);
			cwe.getColumn().setText("CWE");
			cwe.getColumn().setWidth(200);
			cwe.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getCwe());
					}
				}
			});
			sorter.addColumn(cwe, 6);
			
			TreeViewerColumn owasp = new TreeViewerColumn(treeViewer, SWT.LEFT);
			owasp.getColumn().setText("OWASP");
			owasp.getColumn().setWidth(200);
			owasp.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getOwasp());
					}
				}
			});
			sorter.addColumn(owasp, 7);
			
			
			TreeViewerColumn cvss = new TreeViewerColumn(treeViewer, SWT.RIGHT);
			cvss.getColumn().setText("CVSS");
			cvss.getColumn().setWidth(60);
			cvss.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						Float cvss = Float.parseFloat(((SecurityReviewerElement) cell.getElement()).getCvss());
						cell.setText(cvss.toString());
						
						if(cvss != null) {
							if(cvss <= 3) {
								Color color = new Color(Display.getCurrent(), 0, 200, 0);
								cell.setBackground(color);
							} else if(cvss <= 4) {
								Color color = new Color(Display.getCurrent(), 0, 250, 0);
								cell.setBackground(color);
							} else if(cvss <= 5) {
								Color color = new Color(Display.getCurrent(), 255, 250, 0);
								cell.setBackground(color);
							} else if(cvss <= 6) {
								Color color = new Color(Display.getCurrent(), 255, 200, 0);
								cell.setBackground(color);
							} else if(cvss <= 7) {
								Color color = new Color(Display.getCurrent(), 255, 150, 0);
								cell.setBackground(color);
							} else if(cvss <= 8) {
								Color color = new Color(Display.getCurrent(), 255, 100, 0);
								cell.setBackground(color);
							} else if(cvss <= 9) {
								Color color = new Color(Display.getCurrent(), 255, 50, 0);
								cell.setBackground(color);
							} else {
								Color color = new Color(Display.getCurrent(), 255, 0, 0);
								cell.setBackground(color);
							}
						}
					}	
				}
			});
			sorter.addColumn(cvss, 8);
			
			TreeViewerColumn category = new TreeViewerColumn(treeViewer, SWT.LEFT);
			category.getColumn().setText("Category");
			category.getColumn().setWidth(200);
			category.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof SecurityReviewerElementParent)) {
						cell.setText(((SecurityReviewerElement) cell.getElement()).getCategory());
					}
				}
			});
			sorter.addColumn(category, 9);
			
			treeViewer.setContentProvider(new SecurityReviewerViewContentProvider());
			treeViewer.setInput(getViewSite());
			treeViewer.setComparator(sorter);
//		addListenerObject(this);
//		treeViewer.addSelectionChangedListener(new SelectionChangedListener());
			makeActions();
			makeFilters();
			fillLocalToolBar();
			hookDoubleClickAction();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void makeActions() {
		try {
			zipResultAction = new Action() {
				public void run() {
					try{
						String pathDeadCode=SecurityReviewerImportData.getFileDeadCode();
						String pathSecurity=SecurityReviewerImportData.getFileSecurity();
						String rootZipDeadCode=null;
						String rootZipSecurity =null;
						if(pathSecurity!=null){
							rootZipSecurity = new ZipUtility().getZipFolder(pathSecurity);
						}
						if(pathDeadCode!=null){
							rootZipDeadCode = new ZipUtility().getZipFolder(pathDeadCode);
						}
						
						if(rootZipDeadCode!=null && rootZipSecurity!=null && rootZipDeadCode.equals(rootZipSecurity)){
							String dst=new ZipUtility().getDestFolder(pathDeadCode);
							String zipName= new ZipUtility().getZipName(pathDeadCode);
							boolean res=new ZipUtility().compress(rootZipDeadCode, dst, zipName);
							if(res==true)
								new ProcessBuilder("explorer.exe", dst).start();
							else
								MessageDialog.openInformation(treeViewer.getControl().getShell(), "Warning","Error in the export function");
						}					
						else if(rootZipDeadCode!=null && rootZipSecurity!=null && !rootZipDeadCode.equals(rootZipSecurity)){
							String dst1=new ZipUtility().getDestFolder(pathDeadCode);
							String dst2=new ZipUtility().getDestFolder(pathSecurity);
							String zipName1= new ZipUtility().getZipName(pathDeadCode);
							String zipName2= new ZipUtility().getZipName(pathSecurity);
							boolean res1 = new ZipUtility().compress(rootZipDeadCode, dst1, zipName1);
							boolean res2 = new ZipUtility().compress(rootZipSecurity, dst2, zipName2);
							if(res1==true)
								new ProcessBuilder("explorer.exe", dst1).start();
							else
								MessageDialog.openInformation(treeViewer.getControl().getShell(),"Warning", "Error in the export function");
							if(res2==true)
								new ProcessBuilder("explorer.exe", dst2).start();
							else
								MessageDialog.openInformation(treeViewer.getControl().getShell(),"Warning", "Error in the export function");
						}
						else if(rootZipDeadCode!=null && rootZipSecurity==null){
							String dst=new ZipUtility().getDestFolder(pathDeadCode);
							
							String zipName= new ZipUtility().getZipName(pathDeadCode);
							boolean res= new ZipUtility().compress(rootZipDeadCode, dst, zipName);
							if(res==true)
								new ProcessBuilder("explorer.exe", dst).start();
							else
								MessageDialog.openInformation(treeViewer.getControl().getShell(), "Warning","Error in the export function");
						}
						else if(rootZipDeadCode==null && rootZipSecurity!=null){
							String dst=new ZipUtility().getDestFolder(pathSecurity);
							String zipName= new ZipUtility().getZipName(pathSecurity);
							boolean res= new ZipUtility().compress(rootZipSecurity, dst, zipName);
							if(res==true)
								new ProcessBuilder("explorer.exe", dst).start();
							else
								MessageDialog.openInformation(treeViewer.getControl().getShell(), "Warning","Error in the export function");
						}
						else if(rootZipDeadCode==null && rootZipSecurity==null){
							MessageDialog.openInformation(treeViewer.getControl().getShell(), "Warning","Missing deadcode file and security file inside Security Reviewer path");
						}
					}
					
					catch(IOException e){
						LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
					}
					
					
					
				}
			};
			
			zipResultAction.setToolTipText("Zip Result folder");
			zipResultAction.setImageDescriptor(Activator.getImageDescriptor("icons/ZIP.png"));
			
			selectPathAction = new Action(){
				public void run(){
					DirectoryDialog dlg= new DirectoryDialog(
							treeViewer.getControl().getShell());
					dlg.setText("Select the project parent directory");
					String basePath = dlg.open();
					if(basePath!=null){
						SecurityReviewerElement vulnerability = (SecurityReviewerElement) ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
						if(vulnerability.getParent().getElement().contains("Deadcode"))
							SecurityReviewerImportData.setDeadCodePath(basePath);
						else
							SecurityReviewerImportData.setSecurityPath(basePath);
					}
				}
			};
			
			selectPathAction.setToolTipText("Change SourceBasePath");
			selectPathAction.setImageDescriptor(Activator.getImageDescriptor("icons/basepath.gif"));
			
			doubleClickAction = new Action() {
				public void run() {
					String path=null;
					SecurityReviewerElement vulnerability = (SecurityReviewerElement) ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
//					SecurityReviewerElement cwe = (SecurityReviewerElement) ((IStructuredSelection) treeViewer.getSelection()).
					if(vulnerability.getParent().getElement().contains("Deadcode"))
						path = SecurityReviewerImportData.getDeadCodePath();
					else
						path= SecurityReviewerImportData.getSecurityPath();
					if(path==null && (!vulnerability.getElement().contains("Deadcode") && !vulnerability.getElement().contains("Security"))){
						MessageDialog.openInformation(treeViewer.getControl().getShell(), "Error","Missing SourceBasePath inside the artifact");
					}
					else if(checkRoot(vulnerability)==false){
						File fileToOpen = new File(path.trim() + "\\" + vulnerability.getElement());
						if (fileToOpen.exists() && fileToOpen.isFile()) {
							openEditor(fileToOpen, Integer.parseInt(vulnerability.getLine()));
						}
						else{
							MessageDialog.openInformation(treeViewer.getControl().getShell(), "Warning","File not found, select the correct SourceBasePath...");
							DirectoryDialog dlg= new DirectoryDialog(
									treeViewer.getControl().getShell());
							dlg.setText("Select the project parent directory");
							String basePath = dlg.open();
							if(basePath!=null){
								fileToOpen = new File(basePath.trim() + "\\" + vulnerability.getElement());
								if (fileToOpen.exists() && fileToOpen.isFile()) {
									if(vulnerability.getParent().getElement().contains("Deadcode"))
										SecurityReviewerImportData.setDeadCodePath(basePath);
									else
										SecurityReviewerImportData.setSecurityPath(basePath);
									openEditor(fileToOpen, Integer.parseInt(vulnerability.getLine()));
								}
								else{
									MessageDialog.openInformation(treeViewer.getControl().getShell(), "Error","File not found...");
								}
							}
						}
						
					}
				}
			};
			
			importAction = new Action() {
				public void run() {
					try {
						IWizard wizard = PlatformUI.getWorkbench().getImportWizardRegistry().findWizard(SecurityReviewerImport.ID).createWizard();
						WizardDialog wizardDialog = new  WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
						wizardDialog.open();
						
						refresh();
					} catch(Exception exception) {
						LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
					}
				}
			};
			importAction.setToolTipText("Import Security Reviewer files");
			importAction.setImageDescriptor(Activator.getImageDescriptor("icons/import.gif"));
			
			importSecurityReviewerAction = new Action() {
				public void run() {
					try {
						Double version = Double.parseDouble(System.getProperty("java.specification.version"));
						
						String command = "cmd /c start /wait \"\" \"C:\\Security Reviewer\\Client\\srConsole.exe\" -path=" + Platform.getLocation() + " -a=PostSpy -v=1 -d -java -cwe -f=" + version;
						Process processDeadcode = Runtime.getRuntime().exec(command);
						processDeadcode.waitFor();
						
						command = "cmd /c start /wait \"\" \"C:\\Security Reviewer\\Client\\srConsole.exe\" -path=" + Platform.getLocation() + " -a=PostSpy -v=1 -s -java -owasp2013 -f=" + version ;
						Process processSecurity = Runtime.getRuntime().exec(command);
						processSecurity.waitFor();
						
						String deadcodeFileName = "C:\\Security Reviewer\\Client\\Results\\PostSpy\\1\\Deadcode\\01.JAVA Folder\\01." + Platform.getLocation().lastSegment()+ "\\"+ Platform.getLocation().lastSegment() + ".xml";
						String securityFileName = "C:\\Security Reviewer\\Client\\Results\\PostSpy\\1\\Security\\01.JAVA Folder\\01." + Platform.getLocation().lastSegment()+ "\\"+ Platform.getLocation().lastSegment() + ".xml";
						
						SecurityReviewerImportData.importData(deadcodeFileName, securityFileName);
						
						refresh();
					} catch(Exception exception) {
						LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
					}
				}
			};
			importSecurityReviewerAction.setToolTipText("Import from Security Reviewer");
			importSecurityReviewerAction.setImageDescriptor(Activator.getImageDescriptor("icons/SRicon.ico"));
			//importSecurityReviewerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
			
			severity1Action = new Action() {
				public void run() {
					if(severity1) {
						severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1.png"));
						severity1 = false;
					} else {
						severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1_off.png"));
						severity1 = true;
					}
					
					refresh();
				}
			};
			severity1Action.setToolTipText("Filter for severity 1");
			severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1.png"));
			
			severity2Action = new Action() {
				public void run() {
					if(severity2) {
						severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2.png"));
						severity2 = false;
					} else {
						severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2_off.png"));
						severity2 = true;
					}
					
					refresh();
				}
			};
			severity2Action.setToolTipText("Filter for severity 2");
			severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2.png"));
			
			severity3Action = new Action() {
				public void run() {
					if(severity3) {
						severity3Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity3.png"));
						severity3 = false;
					} else {
						severity3Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity3_off.png"));
						severity3 = true;
					}
					
					refresh();
				}
			};
			severity3Action.setToolTipText("Filter for severity 3");
			severity3Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity3.png"));
			
			severity4Action = new Action() {
				public void run() {
					if(severity4) {
						severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4.png"));
						severity4 = false;
					} else {
						severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4_off.png"));
						severity4 = true;
					}
					
					refresh();
				}
			};
			severity4Action.setToolTipText("Filter for severity 4");
			severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4.png"));
			
			severity5Action = new Action() {
				public void run() {
					if(severity5) {
						severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5.png"));
						severity5 = false;
					} else {
						severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5_off.png"));
						severity5 = true;
					}
					
					refresh();
				}
			};
			severity5Action.setToolTipText("Filter for severity 5");
			severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5.png"));			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void makeFilters() {
		try {
			ViewerFilter filter = new ViewerFilter() {
				public boolean select(Viewer viewer, Object parent, Object element) {
					if(element instanceof SecurityReviewerElement) {
						if(severity1 && ((SecurityReviewerElement) element).getSeverity().equals("1")) {
							return false;
						} else if(severity2 && ((SecurityReviewerElement) element).getSeverity().equals("2")) {
							return false;
						} else if(severity3 && ((SecurityReviewerElement) element).getSeverity().equals("3")) {
							return false;
						} else if(severity4 && ((SecurityReviewerElement) element).getSeverity().equals("4")) {
							return false;
						} else if(severity5 && ((SecurityReviewerElement) element).getSeverity().equals("5")) {
							return false;
						}
					}
					
					return true;
				}
			};
			
			treeViewer.addFilter(filter);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillLocalToolBar() {
		try {
			IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
//		File securityReviewer = new File("C:\\Security Reviewer\\Client\\srConsole.exe");
			
			toolBarManager.add(zipResultAction);
			toolBarManager.add(new Separator());
			toolBarManager.add(selectPathAction);
			
			toolBarManager.add(importAction);
			
//		if(securityReviewer.isFile()) {
//			toolBarManager.add(importSecurityReviewerAction);
//		}
			
			toolBarManager.add(new Separator());
			
			toolBarManager.add(severity1Action);
			toolBarManager.add(severity2Action);
			toolBarManager.add(severity3Action);
			toolBarManager.add(severity4Action);
			toolBarManager.add(severity5Action);
			toolBarManager.add(new Separator());
			new DrillDownAdapter(treeViewer).addNavigationActions(toolBarManager);			
		} catch(Exception exception) {
			
		}
	}
	
	private void hookDoubleClickAction() {
		try {
			treeViewer.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					doubleClickAction.run();
				}
			});		 	
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private static boolean checkRoot(SecurityReviewerElement element){
		try {
			if(element.getParent()!=null && element.getParent().getElement().equals(""))
				return true;
			else
				return false;		 	
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return false;
		}
	}
	
	private static void openEditor(File fileToOpen, int lineNumber){
		try{
		 IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
		    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		    IEditorPart editor=IDE.openEditorOnFileStore( page, fileStore );
		    if(editor instanceof ITextEditor){
		    	ITextEditor textEditor = (ITextEditor) editor ;
		    	IDocument document= textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		        textEditor.selectAndReveal(document.getLineOffset(lineNumber - 1), document.getLineLength(lineNumber-1));
		    }
		}
		    catch (NumberFormatException exception) {
		    	LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			}
			catch (BadLocationException exception) {
				LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			} catch (PartInitException exception) {
				LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			}
	}

	public void setFocus() {
		try {
			treeViewer.getControl().setFocus();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}

	public static void refresh() {
		try {
			String ruleset = SecurityReviewerImportData.getRuleset();
			
			if(ruleset != null && (ruleset.equals("OWASP 2010") || ruleset.equals("OWASP 2013"))) {
				treeViewer.getTree().getColumn(3).setText(ruleset);
				treeViewer.getTree().getColumn(3).setWidth(200);
			} else {
				treeViewer.getTree().getColumn(3).setText("");
				treeViewer.getTree().getColumn(3).setWidth(0);
			}
			
			treeViewer.refresh();
			treeViewer.expandAll();						
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
//	private class SelectionChangedListener implements ISelectionChangedListener {
//
//		@Override
//		public void selectionChanged(SelectionChangedEvent event) {
//			SecurityReviewerElement vulnerability = (SecurityReviewerElement) ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
//			if(checkRoot(vulnerability))
//				selectPathAction.setEnabled(false);
//			else
//				selectPathAction.setEnabled(true);
//		}
//	}
		

}