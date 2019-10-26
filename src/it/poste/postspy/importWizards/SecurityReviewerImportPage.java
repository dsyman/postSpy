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

public class SecurityReviewerImportPage extends WizardPage implements Listener {
	public SecurityReviewerImportPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Import Security Reviewer files from the local file system");
	}
	
	private FileFieldEditor deadcodeFileFieldEditor;
	private FileFieldEditor securityFileFieldEditor;
//	private ContainerSelectionGroup containerSelectionGroup;

	public void createControl(Composite parent) {
		try {
//		getShell().setSize(500,370);
			parent = new Composite(parent, SWT.NONE);
			parent.setLayout(new GridLayout());
			
			Composite fileFieldEditorComposite = new Composite(parent, SWT.NONE);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).applyTo(fileFieldEditorComposite);
			
			deadcodeFileFieldEditor = new FileFieldEditor("deadcodeFileSelect", "Select a Security Reviewer Dead Code file : ", fileFieldEditorComposite);
			deadcodeFileFieldEditor.setFileExtensions(new String[] {"*.xml"});
			deadcodeFileFieldEditor.getTextControl(fileFieldEditorComposite).addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					checkPageComplete();
				}
			});
			
			securityFileFieldEditor = new FileFieldEditor("securityFileSelect", "Select a Security Reviewer Security file : ", fileFieldEditorComposite);
			securityFileFieldEditor.setFileExtensions(new String[] {"*.xml"});
			securityFileFieldEditor.getTextControl(fileFieldEditorComposite).addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					checkPageComplete();
				}
			});
			
//		containerSelectionGroup = new ContainerSelectionGroup(parent, this, true);
			
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
			if(deadcodeFileFieldEditor.getStringValue() != "" || securityFileFieldEditor.getStringValue() != "") {
				setPageComplete(true);
			} else {
				setPageComplete(false);
			}			 
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	protected String getDeadcodeFileName() {
		return deadcodeFileFieldEditor.getStringValue();
	}
	
	protected String getSecurityFileName() {
		return securityFileFieldEditor.getStringValue();
	}
	
//	protected String getPath() {
////		if(containerSelectionGroup.getContainerFullPath() != null) {
////			return containerSelectionGroup.getContainerFullPath().toString();
////		} else {
//			return null;
////		}
//	}
}