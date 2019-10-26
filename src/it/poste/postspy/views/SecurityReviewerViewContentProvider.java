package it.poste.postspy.views;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.SecurityReviewerElementParent;
import it.poste.postspy.importWizards.SecurityReviewerImportData;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.model.WorkbenchContentProvider;

@SuppressWarnings("restriction")
public class SecurityReviewerViewContentProvider extends WorkbenchContentProvider {
	public Object[] getElements(Object parent) {
		try {
			if (parent instanceof ViewSite) {
				SecurityReviewerElementParent root = SecurityReviewerImportData.getRoot();
				
				return getChildren(root);
			}
			
			return getChildren(parent);					
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return new Object[0];
		}
	}
	
	public Object[] getChildren(Object parent) {
		try {
			if (parent instanceof SecurityReviewerElementParent) {
				return ((SecurityReviewerElementParent) parent).getChildren();
			}
			
			return new Object[0];			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return new Object[0];
		}
	}
}