package it.poste.postspy.views;

import it.poste.postspy.entity.SecurityReviewerProject;
import it.poste.postspy.entity.SecurityReviewerProjectParent;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class SecurityReviewerProjectsView extends ViewPart {
	
	private static SecurityReviewerProjectParent root;
	
	private TreeViewer treeViewer;

	@Override
	public void createPartControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		treeViewer = new TreeViewer(tree);
		root = new SecurityReviewerProjectParent("Lista Progetti");
		root.addChild(new SecurityReviewerProject("Progetto 1"));
		root.addChild(new SecurityReviewerProject("Progetto 2"));
		root.addChild(new SecurityReviewerProject("Progetto 3"));
		treeViewer.setContentProvider(new SecurityReviewerProjectContentProvider());
		treeViewer.setInput(root);
	}

	@Override
	public void setFocus() {
		
	}

	public static SecurityReviewerProjectParent getRoot() {
		return root;
	}

	public static void setRoot(SecurityReviewerProjectParent root) {
		SecurityReviewerProjectsView.root = root;
	}

}
