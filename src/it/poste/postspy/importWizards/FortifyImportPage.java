package it.poste.postspy.importWizards;


import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class FortifyImportPage extends WizardPage implements Listener {
	public FortifyImportPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Import a Fortify file from the local file system");
	}
	
	private FileFieldEditor fileFieldEditor;
//	private ContainerSelectionGroup containerSelectionGroup;

	public void createControl(Composite parent) {
		try {
//		getShell().setSize(500,300);
			parent = new Composite(parent, SWT.NONE);
			parent.setLayout(new GridLayout());
			
			Composite fileFieldEditorComposite = new Composite(parent, SWT.NONE);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).applyTo(fileFieldEditorComposite);
			
			fileFieldEditor = new FileFieldEditor("fileSelect", "Select a Fortify file : ", fileFieldEditorComposite);
			fileFieldEditor.setFileExtensions(new String[] {"*.fvdl"});
			fileFieldEditor.getTextControl(fileFieldEditorComposite).addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					checkPageComplete();
				}
			});
//		containerSelectionGroup = new ContainerSelectionGroup(parent, this, true);
			fileFieldEditor.fillIntoGrid(fileFieldEditorComposite, 3);
			setControl(parent);
			setPageComplete(false);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}

	public void handleEvent(Event event) {
	}
	
	public void checkPageComplete() {
		try {
			if(fileFieldEditor.getStringValue() != "") {
				setPageComplete(true);
			} else {
				setPageComplete(false);
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	protected String getFileName() {
		return fileFieldEditor.getStringValue();
	}
	
//	protected String getPath() {
////		if(containerSelectionGroup.getContainerFullPath() != null) {
////			return containerSelectionGroup.getContainerFullPath().toString();
////		} else {
//			return null;
////		}
//	}
}