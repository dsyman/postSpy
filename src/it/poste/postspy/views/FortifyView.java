package it.poste.postspy.views;

import it.poste.postspy.Activator;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.FortifyElement;
import it.poste.postspy.entity.FortifyElementParent;
import it.poste.postspy.importWizards.FortifyImport;
import it.poste.postspy.utility.LogUtility;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
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

public class FortifyView extends ViewPart {
	public static final String ID = "it.poste.postspy.views.FortifyView";
	
	private static TreeViewer treeViewer;
	private Action selectPathAction;
	private Action doubleClickAction;
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
			FortifyViewSorter sorter = new FortifyViewSorter();
			
			TreeViewerColumn element = new TreeViewerColumn(treeViewer, SWT.LEFT);
			element.getColumn().setText("Element");
			element.getColumn().setWidth(500);
			element.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					cell.setText(((FortifyElement) cell.getElement()).getElement());
					
					if (cell.getElement() instanceof FortifyElementParent) {
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
					String severity = ((FortifyElement) cell.getElement()).getSeverity();
					
					if(severity.equals("1.0")) {
						cell.setText("1");
						Color color = new Color(Display.getCurrent(), 50, 150, 200);
						cell.setBackground(color);
					} else if(severity.equals("2.0")) {
						cell.setText("2");
						Color color = new Color(Display.getCurrent(), 0, 255, 0);
						cell.setBackground(color);
					} else if(severity.equals("3.0")) {
						cell.setText("3");
						Color color = new Color(Display.getCurrent(), 255, 255, 0);
						cell.setBackground(color);
					} else if(severity.equals("4.0")) {
						cell.setText("4");
						Color color = new Color(Display.getCurrent(), 250, 150, 0);
						cell.setBackground(color);
					} else if(severity.equals("5.0")) {
						cell.setText("5");
						Color color = new Color(Display.getCurrent(), 255, 0, 0);
						cell.setBackground(color);
					}
				}
			});
			sorter.addColumn(severity, 2);
			
			TreeViewerColumn line = new TreeViewerColumn(treeViewer, SWT.RIGHT);
			line.getColumn().setText("Line");
			line.getColumn().setWidth(80);
			line.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getLine());
					}
				}
			});
			sorter.addColumn(line, 3);
			
			TreeViewerColumn description = new TreeViewerColumn(treeViewer, SWT.LEFT);
			description.getColumn().setText("Description");
			description.getColumn().setWidth(500);
			description.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getDescription());
					}
				}
			});
			sorter.addColumn(description, 4);
			
			TreeViewerColumn tip = new TreeViewerColumn(treeViewer, SWT.LEFT);
			tip.getColumn().setText("Tip");
			tip.getColumn().setWidth(500);
			tip.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getTip());
					}
				}
			});
			sorter.addColumn(tip, 5);
			
			TreeViewerColumn cwe = new TreeViewerColumn(treeViewer, SWT.LEFT);
			cwe.getColumn().setText("CWE");
			cwe.getColumn().setWidth(200);
			cwe.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getCwe());
					}
				}
			});
			sorter.addColumn(cwe, 6);
			
			TreeViewerColumn owasp = new TreeViewerColumn(treeViewer, SWT.LEFT);
			owasp.getColumn().setText("OWASP 2010");
			owasp.getColumn().setWidth(200);
			owasp.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getOwasp());
					}
				}
			});
			sorter.addColumn(owasp, 7);
			
			TreeViewerColumn category = new TreeViewerColumn(treeViewer, SWT.LEFT);
			category.getColumn().setText("Category");
			category.getColumn().setWidth(200);
			category.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					if (!(cell.getElement() instanceof FortifyElementParent)) {
						cell.setText(((FortifyElement) cell.getElement()).getCategory());
					}
				}
			});
			sorter.addColumn(category, 8);
			
			treeViewer.setContentProvider(new FortifyViewContentProvider());
			treeViewer.setInput(getViewSite());
			treeViewer.setComparator(sorter);
			
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
			selectPathAction = new Action(){
				public void run(){
					DirectoryDialog dlg= new DirectoryDialog(
							treeViewer.getControl().getShell());
					dlg.setText("Select the project parent directory");
					String basePath = dlg.open();
					if(basePath!=null){
						FortifyImport.setPath(basePath);
					}
				}
			};
			
			selectPathAction.setToolTipText("Change SourceBasePath");
			selectPathAction.setImageDescriptor(Activator.getImageDescriptor("icons/basepath.gif"));
			
			doubleClickAction = new Action() {
				public void run() {
					FortifyElement vulnerability = (FortifyElement) ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
					String path = FortifyImport.getPath();
					if(path==null){
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
									FortifyImport.setPath(basePath);
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
						IWizard wizard = PlatformUI.getWorkbench().getImportWizardRegistry().findWizard(FortifyImport.ID).createWizard();
						WizardDialog wizardDialog = new  WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
						wizardDialog.open();
						
						refresh();
					} catch(Exception exception) {
						//toDo
					}
				}
			};
			importAction.setToolTipText("Import Fortify file");
			importAction.setImageDescriptor(Activator.getImageDescriptor("icons/import.gif"));
			
			severity1Action = new Action() {
				public void run() {
					if(severity1) {
						severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1_Fortify.png"));
						severity1 = false;
					} else {
						severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1_off.png"));
						severity1 = true;
					}
					
					refresh();
				}
			};
			severity1Action.setToolTipText("Filter for severity 1");
			severity1Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity1_Fortify.png"));
			
			severity2Action = new Action() {
				public void run() {
					if(severity2) {
						severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2_Fortify.png"));
						severity2 = false;
					} else {
						severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2_off.png"));
						severity2 = true;
					}
					
					refresh();
				}
			};
			severity2Action.setToolTipText("Filter for severity 2");
			severity2Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity2_Fortify.png"));
			
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
						severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4_Fortify.png"));
						severity4 = false;
					} else {
						severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4_off.png"));
						severity4 = true;
					}
					
					refresh();
				}
			};
			severity4Action.setToolTipText("Filter for severity 4");
			severity4Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity4_Fortify.png"));
			
			severity5Action = new Action() {
				public void run() {
					if(severity5) {
						severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5_Fortify.png"));
						severity5 = false;
					} else {
						severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5_off.png"));
						severity5 = true;
					}
					
					refresh();
				}
			};
			severity5Action.setToolTipText("Filter for severity 5");
			severity5Action.setImageDescriptor(Activator.getImageDescriptor("icons/severity5_Fortify.png"));			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void makeFilters() {
		try {
			ViewerFilter filter = new ViewerFilter() {
				public boolean select(Viewer viewer, Object parent, Object element) {
					if(element instanceof FortifyElement) {
						if(severity1 && ((FortifyElement) element).getSeverity().equals("1.0")) {
							return false;
						} else if(severity2 && ((FortifyElement) element).getSeverity().equals("2.0")) {
							return false;
						} else if(severity3 && ((FortifyElement) element).getSeverity().equals("3.0")) {
							return false;
						} else if(severity4 && ((FortifyElement) element).getSeverity().equals("4.0")) {
							return false;
						} else if(severity5 && ((FortifyElement) element).getSeverity().equals("5.0")) {
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
			toolBarManager.add(selectPathAction);
			toolBarManager.add(importAction);
			toolBarManager.add(new Separator());
			toolBarManager.add(severity5Action);
			toolBarManager.add(severity4Action);
			toolBarManager.add(severity3Action);
			toolBarManager.add(severity2Action);
			toolBarManager.add(severity1Action);
			toolBarManager.add(new Separator());
			new DrillDownAdapter(treeViewer).addNavigationActions(toolBarManager);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
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
	
	private static boolean checkRoot(FortifyElement element){
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
			treeViewer.refresh();
			treeViewer.expandAll();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
}