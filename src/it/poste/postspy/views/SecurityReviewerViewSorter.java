package it.poste.postspy.views;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.SecurityReviewerElement;
import it.poste.postspy.entity.SecurityReviewerElementParent;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TreeColumn;

public class SecurityReviewerViewSorter extends ViewerComparator {
	private final ViewerComparator elementSorter = new JavaElementComparator();
	private int column = 1;
	private int order = SWT.UP;
	
	public void addColumn(TreeViewerColumn treeViewerColumn, final int columnidx) {
		try {
			final TreeColumn treeColumn = treeViewerColumn.getColumn();
			
			treeColumn.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent selectionEvent) {
					treeColumn.getParent().setSortColumn(treeColumn);
					column = columnidx;
					
					if(treeColumn.getParent().getSortDirection() == SWT.DOWN) {
						treeColumn.getParent().setSortDirection(SWT.UP);
						order = SWT.UP;
					} else {
						treeColumn.getParent().setSortDirection(SWT.DOWN);
						order = SWT.DOWN;
					}
					
					SecurityReviewerView.refresh();
				}
				
				public void widgetDefaultSelected(SelectionEvent selectionEvent) {
				}
			});			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	public int compare(Viewer viewer, Object object1, Object object2) {
		int res = 0;
		try {
			if (object1 instanceof SecurityReviewerElementParent || object2 instanceof SecurityReviewerElementParent) {
				return 0;
			}
			
			switch (column) {
			case 1:
				break;
			case 2:
				try {
					res = Double.compare(Integer.parseInt(((SecurityReviewerElement) object1).getSeverity()), Integer.parseInt(((SecurityReviewerElement) object2).getSeverity()));
					
					if(res == 0) {
						res = Float.compare(Float.parseFloat(((SecurityReviewerElement) object1).getCvss()), Float.parseFloat(((SecurityReviewerElement) object2).getCvss()));
					}
				} catch (Exception exception) {
					return 0;
				}
				
				break;
			case 3:
				res = Double.compare(Integer.parseInt(((SecurityReviewerElement) object1).getLine()), Integer.parseInt(((SecurityReviewerElement) object2).getLine()));
				
				
				break;
			case 4:
				res = ((SecurityReviewerElement) object1).getDescription().compareTo(((SecurityReviewerElement) object2).getDescription());
				
				
				break;
			case 5:
				res = ((SecurityReviewerElement) object1).getTip().compareTo(((SecurityReviewerElement) object2).getTip());
				
				
				break;
			case 6: 
				res = ((SecurityReviewerElement) object1).getCwe().compareTo(((SecurityReviewerElement) object2).getCwe());
				break;
			case 7:
				res = ((SecurityReviewerElement) object1).getOwasp().compareTo(((SecurityReviewerElement) object2).getOwasp());
				break;
			case 8:
				try {
					res = Float.compare(Float.parseFloat(((SecurityReviewerElement) object1).getCvss()), Float.parseFloat(((SecurityReviewerElement) object2).getCvss()));
					
					if(res == 0) {
						res = Double.compare(Integer.parseInt(((SecurityReviewerElement) object1).getSeverity()), Integer.parseInt(((SecurityReviewerElement) object2).getSeverity()));
					}
				} catch (Exception exception) {	
					return 0;
				}
				break;
			case 9:
				res = ((SecurityReviewerElement) object1).getCategory().compareTo(((SecurityReviewerElement) object2).getCategory());
				break;
				
			}
			
			if(res == 0) {
				res = elementSorter.compare(viewer, ((SecurityReviewerElement) object1).getElement(), ((SecurityReviewerElement) object2).getElement());
				
				if(res == 0) {
					res = Double.compare(Integer.parseInt(((SecurityReviewerElement) object1).getLine()), Integer.parseInt(((SecurityReviewerElement) object2).getLine()));
				}
			}
			
			if(order == SWT.UP) {
				return res;
			} else {
				return res * -1;
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return res * -1;
		}
	}
}