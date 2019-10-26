package it.poste.postspy.button.actions;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.views.SecurityReviewerView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class SecurityReviewerAction implements IWorkbenchWindowActionDelegate {
	public void run(IAction iAction) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SecurityReviewerView.ID);
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}

	public void selectionChanged(IAction iAction, ISelection iSelection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow iWorkbenchWindow) {
	}
}