package it.poste.postspy.entity;

public class SecurityReviewerElement {
	private String element;
	private String severity;
	private String cwe;
	private String owasp;
	private String cvss;
	private String line;
	private String description;
	private String tip;
	private String category;
	
	private SecurityReviewerElement parent;
	
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
	
	public String getCvss() {
		return cvss;
	}
	
	public void setCvss(String cvss) {
		this.cvss = cvss;
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
		
	
	public SecurityReviewerElement getParent() {
		return parent;
	}
	
	

	public void setParent(SecurityReviewerElement parent) {
		this.parent = parent;
	}
}