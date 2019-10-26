package it.poste.postspy.entity;

public class SecurityReviewerProject {
	
	public SecurityReviewerProject(String name) {
		this.name = name;
	}
	
	private String name;
	
	private SecurityReviewerProject parent;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public SecurityReviewerProject getParent() {
		return parent;
	}
	public void setParent(SecurityReviewerProject parent) {
		this.parent = parent;
	}

}
