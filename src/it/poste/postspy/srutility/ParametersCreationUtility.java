package it.poste.postspy.srutility;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.WizardParametersConstant;
import it.poste.postspy.entity.ParametersFilter;
import it.poste.postspy.entity.SrConsole;
import it.poste.postspy.entity.SrConsole.Parameter;
import it.poste.postspy.utility.LogUtility;

import java.util.HashMap;

public class ParametersCreationUtility {

	private ParametersFilter parametersFilter;
	
	public ParametersCreationUtility(ParametersFilter parametersFilter) {
		this.parametersFilter = parametersFilter;
	}
	
	public SrConsole create() {
		try {
			SrConsole srConsole = new SrConsole();
			
			String browserString = createBrowserString();
			String severityString = createSeverityString();
			
			Parameter parameter;
			parameter = ParametersValueTransform.getPathParam(parametersFilter.getProjectPath());
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getApplicationNameParam(parametersFilter.getProjectName());
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getApplicationVersionParam(parametersFilter.getProjectVersion());
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getAuditorParam(parametersFilter.getAuditor());
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getAuditDateParam(parametersFilter.getAuditDate());
			srConsole.getInputParameters().add(parameter);
			if(parametersFilter.isQualityAnalysis()) {
				parameter = ParametersNoValueTransform.getQualityAnalysisParam();
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.isStaticAnalysis()) {
				parameter = ParametersNoValueTransform.getStaticAnalysisParam();
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.isBaseline()) {
				parameter = ParametersNoValueTransform.getBaselineParam();
				srConsole.getInputParameters().add(parameter);			
			}
			if(parametersFilter.getIncrementalFilePath()!=null) {
				parameter = ParametersValueTransform.getIncrementalParam(parametersFilter.getIncrementalFilePath());
				srConsole.getInputParameters().add(parameter);
			}
			
			parameter = getLanguageParam();
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getSqlDialectParam(parametersFilter.getSqlDialect());
			srConsole.getInputParameters().add(parameter);
			parameter = ParametersValueTransform.getFrameworkParam(parametersFilter.getFramework());
			srConsole.getInputParameters().add(parameter);
			if(WizardParametersConstant.LOAD_BY_SOLUTION.equals(parametersFilter.getLoadType())) {
				parameter = ParametersNoValueTransform.getSolutionLoadtypeParam();
				srConsole.getInputParameters().add(parameter);
			} else if(WizardParametersConstant.LOAD_BY_PROJECT.equals(parametersFilter.getLoadType())) {
				parameter = ParametersNoValueTransform.getProjectLoadtypeParam();
				srConsole.getInputParameters().add(parameter);
			} else if(WizardParametersConstant.LOAD_BY_CLASSPATH.equals(parametersFilter.getLoadType())) {
				parameter = ParametersNoValueTransform.getClasspathLoadtypeParam();
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.isIaApplication()) {
				parameter = ParametersNoValueTransform.getInternetApplicationParam();
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.isCaApplication()) {
				parameter = ParametersNoValueTransform.getConsoleApplicationParam();
				srConsole.getInputParameters().add(parameter);			
			}
			if(!"".equals(browserString)) {
				parameter = ParametersValueTransform.getBrowserParam(browserString);
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.getFolderCopyPath()!=null) {
				parameter = ParametersValueTransform.getFolderCopyParam(parametersFilter.getFolderCopyPath());
				srConsole.getInputParameters().add(parameter);
			}
			parameter = ParametersValueTransform.getMaxVulnsParam(parametersFilter.getMaxVulns());
			srConsole.getInputParameters().add(parameter);
			parameter = getSecurityRuleSetParam();
			srConsole.getInputParameters().add(parameter);
			if(parametersFilter.isExclusionList()) {
				parameter = ParametersNoValueTransform.getExclusionListParam();
				srConsole.getInputParameters().add(parameter);
			}
			if(!"".equals(severityString)) {
				parameter = ParametersValueTransform.getSeverityParam(severityString);
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.getCustomRulesFilePath()!=null) {
				parameter = ParametersValueTransform.getCustomRulesParam(parametersFilter.getCustomRulesFilePath());
				srConsole.getInputParameters().add(parameter);
			}
			if(parametersFilter.isDbQueries()) {
				parameter = ParametersNoValueTransform.getDBQueriesParam();
				srConsole.getInputParameters().add(parameter);			
			}
			if(parametersFilter.isSocket()) {
				parameter = ParametersNoValueTransform.getSocketParam();
				srConsole.getInputParameters().add(parameter);			
			}
			if(parametersFilter.isEnvironmentVariables()) {
				parameter = ParametersNoValueTransform.getEnvironmentVariablesParam();
				srConsole.getInputParameters().add(parameter);			
			}
			if(parametersFilter.isServletRequest()) {
				parameter = ParametersNoValueTransform.getServeltRequestParam();
				srConsole.getInputParameters().add(parameter);			
			}
			
			return srConsole;
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
			return null;
		}
	}
	
	private String createBrowserString() {
		String browserString = "";
		HashMap<String, String> browserCodeVersionList = createBrowserCodeVersionList();
		String ieString = parametersFilter.getIeVersion();
		String chromeString = parametersFilter.getChromeVersion();
		String firefoxString = parametersFilter.getFirefoxVersion();
		String safariString = parametersFilter.getOperaVersion();
		String operaString = parametersFilter.getSafariVersion();
		if(ieString!=null) {
			browserString += browserCodeVersionList.get(ieString) + ",";
		}
		if(chromeString!=null) {
			browserString += browserCodeVersionList.get(chromeString) + ",";
		}
		if(firefoxString!=null) {
			browserString += browserCodeVersionList.get(firefoxString) + ",";
		}
		if(safariString!=null) {
			browserString += browserCodeVersionList.get(safariString) + ",";
		}
		if(operaString!=null) {
			browserString += browserCodeVersionList.get(operaString) + ",";
		}
		return browserString.length()>0?browserString.substring(0, browserString.length()-1):"";
	}
	
	private String createSeverityString() {
		String severityString = "";
		if(parametersFilter.isSev1()) {
			severityString += "1,";
		}
		if(parametersFilter.isSev2()) {
			severityString += "2,";	
		}
		if(parametersFilter.isSev3()) {
			severityString += "3,";
		}
		if(parametersFilter.isSev4()) {
			severityString += "4,";
		}
		if(parametersFilter.isSev5()) {
			severityString += "5,";
		}
		return severityString.length()>0?severityString.substring(0, severityString.length()-1):"";
	}
	
	private Parameter getSecurityRuleSetParam() {
		Parameter parameter = null;
		if(WizardParametersConstant.RULE_SET_OWASP2010.equals(parametersFilter.getSecurityRuleSet())) {
			parameter = ParametersNoValueTransform.getOwasp2010Param();
		} else if(WizardParametersConstant.RULE_SET_OWASP2013.equals(parametersFilter.getSecurityRuleSet())) {
			parameter = ParametersNoValueTransform.getOwasp2013Param();
		} else if(WizardParametersConstant.RULE_SET_OWASP_MOBILE_2014.equals(parametersFilter.getSecurityRuleSet())) {
			parameter = ParametersNoValueTransform.getOwaspMobile2014Param();
		} else if(WizardParametersConstant.RULE_SET_CWE.equals(parametersFilter.getSecurityRuleSet())) {
			parameter = ParametersNoValueTransform.getCweParam();
		}
		return parameter;
	}
	
//	private Parameter getDeadCodeRuleSetParam() {
//		Parameter parameter = null;
//		if(WizardParametersConstant.RULE_SET_OWASP2010.equals(parametersFilter.getDeadcodeRuleSet())) {
//			parameter = ParametersNoValueTransform.getOwasp2010Param();
//		} else if(WizardParametersConstant.RULE_SET_OWASP2013.equals(parametersFilter.getDeadcodeRuleSet())) {
//			parameter = ParametersNoValueTransform.getOwasp2013Param();
//		} else if(WizardParametersConstant.RULE_SET_OWASP_MOBILE_2014.equals(parametersFilter.getDeadcodeRuleSet())) {
//			parameter = ParametersNoValueTransform.getOwaspMobile2014Param();
//		} else if(WizardParametersConstant.RULE_SET_CWE.equals(parametersFilter.getDeadcodeRuleSet())) {
//			parameter = ParametersNoValueTransform.getCweParam(); 
//		}
//		return parameter;
//	}
	
	private Parameter getLanguageParam() {
		Parameter parameter = null;
		if(WizardParametersConstant.LANGUAGE_ABAP.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getAbapLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_ANDROID.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getAndroidLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_COBOL.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getCobolLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_CPP.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getCppLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_CSHARP.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getCsharpLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_IOS.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getIosLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_JAVA.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getJavaLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_PHP.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getPhpLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_PYTHON.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getPythonLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_RUBY.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getRubyLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_SQL.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getSqlLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_VBNET.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getVbetLanguageParam();
		} else if(WizardParametersConstant.LANGUAGE_VISUAL_BASIC.equals(parametersFilter.getLanguage())) {
			parameter = ParametersNoValueTransform.getVisualbasicLanguageParam();
		}
		return parameter;
	}

	private HashMap<String, String> createBrowserCodeVersionList() {
		HashMap<String, String> browserCodeVersionList = new HashMap<String, String>();
		browserCodeVersionList.put(WizardParametersConstant.BROWSER_IE_ALL_VERSION.split("/")[0], WizardParametersConstant.BROWSER_IE_ALL_VERSION.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_1.split("/")[0], WizardParametersConstant.IE_1.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_2.split("/")[0], WizardParametersConstant.IE_2.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_3.split("/")[0], WizardParametersConstant.IE_3.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_4.split("/")[0], WizardParametersConstant.IE_4.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_5.split("/")[0], WizardParametersConstant.IE_5.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_6.split("/")[0], WizardParametersConstant.IE_6.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_7.split("/")[0], WizardParametersConstant.IE_7.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.IE_8.split("/")[0], WizardParametersConstant.IE_8.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.BROWSER_CHROME_ALL_VERSION.split("/")[0], WizardParametersConstant.BROWSER_CHROME_ALL_VERSION.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.CHROME_1.split("/")[0], WizardParametersConstant.CHROME_1.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.CHROME_2.split("/")[0], WizardParametersConstant.CHROME_2.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.CHROME_3.split("/")[0], WizardParametersConstant.CHROME_3.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.BROWSER_FIREFOX_ALL_VERSION.split("/")[0], WizardParametersConstant.BROWSER_FIREFOX_ALL_VERSION.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_1.split("/")[0], WizardParametersConstant.FIREFOX_1.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_2.split("/")[0], WizardParametersConstant.FIREFOX_2.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_3.split("/")[0], WizardParametersConstant.FIREFOX_3.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_4.split("/")[0], WizardParametersConstant.FIREFOX_4.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_5.split("/")[0], WizardParametersConstant.FIREFOX_5.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_6.split("/")[0], WizardParametersConstant.FIREFOX_6.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_7.split("/")[0], WizardParametersConstant.FIREFOX_7.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_8.split("/")[0], WizardParametersConstant.FIREFOX_8.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_9.split("/")[0], WizardParametersConstant.FIREFOX_9.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.FIREFOX_10.split("/")[0], WizardParametersConstant.FIREFOX_10.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_1.split("/")[0], WizardParametersConstant.SAFARI_1.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_2.split("/")[0], WizardParametersConstant.SAFARI_2.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_3.split("/")[0], WizardParametersConstant.SAFARI_3.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_4.split("/")[0], WizardParametersConstant.SAFARI_4.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_5.split("/")[0], WizardParametersConstant.SAFARI_5.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_6.split("/")[0], WizardParametersConstant.SAFARI_6.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_7.split("/")[0], WizardParametersConstant.SAFARI_7.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_8.split("/")[0], WizardParametersConstant.SAFARI_8.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_9.split("/")[0], WizardParametersConstant.SAFARI_9.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.SAFARI_10.split("/")[0], WizardParametersConstant.SAFARI_10.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.BROWSER_OPERA_ALL_VERSION.split("/")[0], WizardParametersConstant.BROWSER_OPERA_ALL_VERSION.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_1.split("/")[0], WizardParametersConstant.OPERA_1.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_2.split("/")[0], WizardParametersConstant.OPERA_2.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_3.split("/")[0], WizardParametersConstant.OPERA_3.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_4.split("/")[0], WizardParametersConstant.OPERA_4.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_5.split("/")[0], WizardParametersConstant.OPERA_5.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_6.split("/")[0], WizardParametersConstant.OPERA_6.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_7.split("/")[0], WizardParametersConstant.OPERA_7.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_8.split("/")[0], WizardParametersConstant.OPERA_8.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_9.split("/")[0], WizardParametersConstant.OPERA_9.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_10.split("/")[0], WizardParametersConstant.OPERA_10.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_11.split("/")[0], WizardParametersConstant.OPERA_11.split("/")[1]);
		browserCodeVersionList.put(WizardParametersConstant.OPERA_12.split("/")[0], WizardParametersConstant.OPERA_12.split("/")[1]);
		return browserCodeVersionList;
	}
	
}
