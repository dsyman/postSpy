package it.poste.postspy.importWizards;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.views.SecurityReviewerView;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class SecurityReviewerImport extends Wizard implements IImportWizard {
	public static final String ID = "it.poste.postspy.importWizards.SecurityReviewerImport";
	
	public SecurityReviewerImport() {
		super();
		setWindowTitle("File Import Wizard");
    	setNeedsProgressMonitor(true);
	}
	
	private SecurityReviewerImportPage securityReviewerImportPage;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public boolean performFinish() {
		try {
			SecurityReviewerImportData.importData(securityReviewerImportPage.getDeadcodeFileName(), securityReviewerImportPage.getSecurityFileName());
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SecurityReviewerView.ID);
			
			SecurityReviewerView.refresh();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
		
		return true;
	}
	
	public void addPages() {
		try {
			securityReviewerImportPage = new SecurityReviewerImportPage("Import Security Reviewer files");
			addPage(securityReviewerImportPage);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
    }
}