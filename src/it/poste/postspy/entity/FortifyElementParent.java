package it.poste.postspy.entity;

import java.util.ArrayList;

public class FortifyElementParent extends FortifyElement {
	private ArrayList<FortifyElement> children;
	
	public FortifyElementParent(String element, String severity, String cwe, String owasp, String category, String line, String description) {
		super.setElement(element);
		super.setSeverity(severity);
		super.setCwe(cwe);
		super.setOwasp(owasp);
		super.setCategory(category);
		super.setLine(line);
		super.setDescription(description);
		children = new ArrayList<FortifyElement>();
	}
	
	public void addChild(FortifyElement child) {
		children.add(child);
		child.setParent(this);
	}
	
	public FortifyElement[] getChildren() {
		return (FortifyElement[]) children.toArray(new FortifyElement[children.size()]);
	}
}