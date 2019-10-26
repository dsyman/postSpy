package it.poste.postspy.entity;

import java.util.ArrayList;

public class SecurityReviewerProjectParent extends SecurityReviewerProject {
	
	private ArrayList<SecurityReviewerProject> children;
	
	public SecurityReviewerProjectParent(String name) {
		super(name);
		children = new ArrayList<SecurityReviewerProject>();
	}
	
	public void addChild(SecurityReviewerProject child) {
		children.add(child);
		child.setParent(this);
	}
	
	public SecurityReviewerProject[] getChildren() {
		return (SecurityReviewerProject[]) children.toArray(new SecurityReviewerProject[children.size()]);
	}

}
