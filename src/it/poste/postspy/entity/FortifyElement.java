package it.poste.postspy.entity;

public class FortifyElement {
	private String element;
	private String severity;
	private String cwe;
	private String owasp;
	private String category;
	private String line;
	private String description;
	private String tip;
	private FortifyElementParent parent;
	
	public String getElement() {
		return element;
	}
	
	public void setElement(String element) {
		this.element = element;
	}
	
	public String getSeverity() {
		return severity;
	}
	
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	public String getCwe() {
		return cwe;
	}
	
	public void setCwe(String cwe) {
		this.cwe = cwe;
	}
	
	public String getOwasp() {
		return owasp;
	}
	
	public void setOwasp(String owasp) {
		this.owasp = owasp;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getLine() {
		return line;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTip() {
		return tip;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public FortifyElementParent getParent() {
		return parent;
	}

	public void setParent(FortifyElementParent parent) {
		this.parent = parent;
	}
}