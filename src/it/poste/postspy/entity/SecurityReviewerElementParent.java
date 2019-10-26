package it.poste.postspy.entity;

import java.util.ArrayList;

public class SecurityReviewerElementParent extends SecurityReviewerElement {
	private ArrayList<SecurityReviewerElement> children;
	
	public SecurityReviewerElementParent(String element, String severity, String cwe, String owasp, String cvss, String line, String description) {
		super.setElement(element);
		super.setSeverity(severity);
		super.setCwe(cwe);
		super.setOwasp(owasp);
		super.setCvss(cvss);
		super.setLine(line);
		super.setDescription(description);
		children = new ArrayList<SecurityReviewerElement>();
	}
	
	public void addChild(SecurityReviewerElement child) {
		children.add(child);
		child.setParent(this);
	}
	
	public SecurityReviewerElement[] getChildren() {
		return (SecurityReviewerElement[]) children.toArray(new SecurityReviewerElement[children.size()]);
	}
}