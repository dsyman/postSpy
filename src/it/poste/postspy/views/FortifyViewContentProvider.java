package it.poste.postspy.views;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.FortifyElementParent;
import it.poste.postspy.importWizards.FortifyImport;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.model.WorkbenchContentProvider;

@SuppressWarnings("restriction")
public class FortifyViewContentProvider extends WorkbenchContentProvider {
	public Object[] getElements(Object parent) {
		try {
			if (parent instanceof ViewSite) {
				FortifyElementParent root = FortifyImport.getRoot();
				
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
			if (parent instanceof FortifyElementParent) {
				return ((FortifyElementParent) parent).getChildren();
			}
			return new Object[0];
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return new Object[0];
		}		
	}
}