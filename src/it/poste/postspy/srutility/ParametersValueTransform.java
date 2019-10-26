package it.poste.postspy.srutility;

import it.poste.postspy.constant.SrConsoleConstant;
import it.poste.postspy.entity.SrConsole.Parameter;

public class ParametersValueTransform {
	
	public static Parameter getApplicationNameParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.APPLICATION_NAME);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getApplicationVersionParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.APPLICATION_VERSION);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getAuditorParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.AUDITOR);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getAuditDateParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.AUDIT_DATE);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getBrowserParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.TARGET_BROWSER);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getSeverityParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.SEVERITY);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getIncrementalParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.INCREMENTAL);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getPathParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.APPLICATION_PATH);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getFrameworkParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.FRAMEWORK);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getFolderCopyParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.FOLDER_COPY);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getMaxVulnsParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.MAX_VULNERABILITIES);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getCustomRulesParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.CUSTOM_RULES);
		parameter.setValue(value);
		return parameter;
	}
	
	public static Parameter getSqlDialectParam(String value) {
		Parameter parameter = new Parameter();
		parameter.setName(SrConsoleConstant.LANGUAGE_SQL);
		parameter.setValue(value);
		return parameter;
	}

}
