package it.poste.postspy.entity;

public class ParametersFilter {
	
	private String projectPath;
	private String projectName;
	private String projectVersion;
	private String auditor;
	private String auditDate;
	private boolean qualityAnalysis;
	private boolean staticAnalysis;
	private boolean baseline;
	private String incrementalFilePath;
	private boolean remoteScan;
	private String previousVersion;
	
	private String language;
	private String sqlDialect;
	private String framework;
	private String loadType;
	private boolean iaApplication;
	private boolean caApplication;
	private String ieVersion;
	private String chromeVersion;
	private String firefoxVersion;
	private String safariVersion;
	private String operaVersion;
	private String folderCopyPath;
	private String maxVulns;
	private String securityRuleSet;
	private String deadcodeRuleSet;
	private boolean exclusionList;
	private boolean sev1;
	private boolean sev2;
	private boolean sev3;
	private boolean sev4;
	private boolean sev5;
	private String customRulesFilePath;
	private boolean dbQueries;
	private boolean socket;
	private boolean environmentVariables;
	private boolean servletRequest;
	
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public boolean isQualityAnalysis() {
		return qualityAnalysis;
	}
	public void setQualityAnalysis(boolean qualityAnalysis) {
		this.qualityAnalysis = qualityAnalysis;
	}
	public boolean isStaticAnalysis() {
		return staticAnalysis;
	}
	public void setStaticAnalysis(boolean staticAnalysis) {
		this.staticAnalysis = staticAnalysis;
	}
	public boolean isBaseline() {
		return baseline;
	}
	public void setBaseline(boolean baseline) {
		this.baseline = baseline;
	}
	public String getIncrementalFilePath() {
		return incrementalFilePath;
	}
	public void setIncrementalFilePath(String incrementalFilePath) {
		this.incrementalFilePath = incrementalFilePath;
	}
	public boolean isRemoteScan() {
		return remoteScan;
	}
	public void setRemoteScan(boolean remoteScan) {
		this.remoteScan = remoteScan;
	}
	public String getPreviousVersion() {
		return previousVersion;
	}
	public void setPreviousVersion(String previousVersion) {
		this.previousVersion = previousVersion;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSqlDialect() {
		return sqlDialect;
	}
	public void setSqlDialect(String sqlDialect) {
		this.sqlDialect = sqlDialect;
	}
	public String getFramework() {
		return framework;
	}
	public void setFramework(String framework) {
		this.framework = framework;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public boolean isIaApplication() {
		return iaApplication;
	}
	public void setIaApplication(boolean iaApplication) {
		this.iaApplication = iaApplication;
	}
	public boolean isCaApplication() {
		return caApplication;
	}
	public void setCaApplication(boolean caApplication) {
		this.caApplication = caApplication;
	}
	public String getIeVersion() {
		return ieVersion;
	}
	public void setIeVersion(String ieVersion) {
		this.ieVersion = ieVersion;
	}
	public String getChromeVersion() {
		return chromeVersion;
	}
	public void setChromeVersion(String chromeVersion) {
		this.chromeVersion = chromeVersion;
	}
	public String getFirefoxVersion() {
		return firefoxVersion;
	}
	public void setFirefoxVersion(String firefoxVersion) {
		this.firefoxVersion = firefoxVersion;
	}
	public String getSafariVersion() {
		return safariVersion;
	}
	public void setSafariVersion(String safariVersion) {
		this.safariVersion = safariVersion;
	}
	public String getOperaVersion() {
		return operaVersion;
	}
	public void setOperaVersion(String operaVersion) {
		this.operaVersion = operaVersion;
	}
	public String getFolderCopyPath() {
		return folderCopyPath;
	}
	public void setFolderCopyPath(String folderCopyPath) {
		this.folderCopyPath = folderCopyPath;
	}
	public String getMaxVulns() {
		return maxVulns;
	}
	public void setMaxVulns(String maxVulns) {
		this.maxVulns = maxVulns;
	}
	public String getSecurityRuleSet() {
		return securityRuleSet;
	}
	public void setSecurityRuleSet(String securityRuleSet) {
		this.securityRuleSet = securityRuleSet;
	}
	public String getDeadcodeRuleSet() {
		return deadcodeRuleSet;
	}
	public void setDeadcodeRuleSet(String deadcodeRuleSet) {
		this.deadcodeRuleSet = deadcodeRuleSet;
	}
	public boolean isExclusionList() {
		return exclusionList;
	}
	public void setExclusionList(boolean exclusionList) {
		this.exclusionList = exclusionList;
	}
	public boolean isSev1() {
		return sev1;
	}
	public void setSev1(boolean sev1) {
		this.sev1 = sev1;
	}
	public boolean isSev2() {
		return sev2;
	}
	public void setSev2(boolean sev2) {
		this.sev2 = sev2;
	}
	public boolean isSev3() {
		return sev3;
	}
	public void setSev3(boolean sev3) {
		this.sev3 = sev3;
	}
	public boolean isSev4() {
		return sev4;
	}
	public void setSev4(boolean sev4) {
		this.sev4 = sev4;
	}
	public boolean isSev5() {
		return sev5;
	}
	public void setSev5(boolean sev5) {
		this.sev5 = sev5;
	}
	public String getCustomRulesFilePath() {
		return customRulesFilePath;
	}
	public void setCustomRulesFilePath(String customRulesFilePath) {
		this.customRulesFilePath = customRulesFilePath;
	}
	public boolean isDbQueries() {
		return dbQueries;
	}
	public void setDbQueries(boolean dbQueries) {
		this.dbQueries = dbQueries;
	}
	public boolean isSocket() {
		return socket;
	}
	public void setSocket(boolean socket) {
		this.socket = socket;
	}
	public boolean isEnvironmentVariables() {
		return environmentVariables;
	}
	public void setEnvironmentVariables(boolean environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	public boolean isServletRequest() {
		return servletRequest;
	}
	public void setServletRequest(boolean servletRequest) {
		this.servletRequest = servletRequest;
	}

}
