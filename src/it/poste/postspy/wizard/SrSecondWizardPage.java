package it.poste.postspy.wizard;

import it.poste.postspy.constant.FileSystemConstant;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.WizardParametersConstant;
import it.poste.postspy.shell.BrowserShell;
import it.poste.postspy.shell.CPPShell;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class SrSecondWizardPage extends WizardPage {
	
	private SRWizard srWizard;
	
	public static final String C_GENERIC_OPTION = "generic";
	public static final String C_UNIX_OPTION = "unix";
	public static final String C_WIN_OPTION = "win";
	
	private Shell shell;
	
	private Composite container;
	private GridLayout layout;
	private Label languageLabel;
	private Combo languageCombo;
	private Label sqlDialectLabel;
	private Combo sqlDialectCombo;
	private Label frameworkLabel;
	private Combo frameworkCombo;
	private Label loadTypeLabel;
	private Combo loadTypeCombo;
	private Button internetApplicationCheck;
//	private Button consoleApplicationCheck;
	private Button targetBrowserCheck;
//	private Label folderCopyLabel;
//	private Button folderCopyButton;
	private Label maxVulnsLabel;
	private Spinner maxVulnsSpinner;
	private Label ruleSetSecurityLabel;
	private Combo ruleSetSecurityCombo;
//	private Label ruleSetDeadcodeLabel;
//	private Combo ruleSetDeadcodeCombo;
	private Button exclusionListCheck;
	private Button sev1Check;
	private Button sev2Check;
	private Button sev3Check;
	private Button sev4Check;
	private Button sev5Check;
	private Label customLabel;
	private Button customButton;
	private Group trustedGroup;
	private Button trustedApplicationCheck;
	private Button publicFunctionCheck;
	private Button dbQueriesCheck;
	private Button socketCheck;
	private Button environmentCheck;
	private Button servletCheck;
	
	private BrowserShell browserShell;
	
	private String copybookFolderPath, customPath, selection,
	ieVersion, chromeVersion, firefoxVersion, safariVersion, operaVersion;
	
	public Combo getLanguageCombo() {
		return languageCombo;
	}
	public Combo getSqlDialectCombo() {
		return sqlDialectCombo;
	}
	public Combo getFrameworkCombo() {
		return frameworkCombo;
	}
	public Combo getLoadTypeCombo() {
		return loadTypeCombo;
	}
	public Button getInternetApplicationCheck() {
		return internetApplicationCheck;
	}
//	public Button getConsoleApplicationCheck() {
//		return consoleApplicationCheck;
//	}
	public Button getTargetBrowserCheck() {
		return targetBrowserCheck;
	}
//	public Button getFolderCopyButton() {
//		return folderCopyButton;
//	}
	public Spinner getMaxVulnsSpinner() {
		return maxVulnsSpinner;
	}
	public Combo getRuleSetSecurityCombo() {
		return ruleSetSecurityCombo;
	}
//	public Combo getRuleSetDeadcodeCombo() {
//		return ruleSetDeadcodeCombo;
//	}
	public Button getExclusionListCheck() {
		return exclusionListCheck;
	}
	public Button getSev1Check() {
		return sev1Check;
	}
	public Button getSev2Check() {
		return sev2Check;
	}
	public Button getSev3Check() {
		return sev3Check;
	}
	public Button getSev4Check() {
		return sev4Check;
	}
	public Button getSev5Check() {
		return sev5Check;
	}
	public Button getCustomButton() {
		return customButton;
	}
	
	public String getCopybookFolderPath() {
		return copybookFolderPath;
	}
	public String getCustomPath() {
		return customPath;
	}
	public void setSelection(String selection) {
		this.selection = selection;
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
	public SrSecondWizardPage(Shell shell, SRWizard srWizard) {
		super("wizardPage");
		this.shell = shell;
		this.srWizard = srWizard;
		setTitle("Security Reviewer Scan Options 2/2");
	}

	public void createControl(Composite parent) {
		try {
			getShell().setSize(750, 670);
			getShell().layout(true, true);
			inizializeControls(parent);
			inizializeLanguageCombo();
			selectFirstComboElement();
			
			container.setLayout(layout);
			layout.numColumns = 5;
			layout.verticalSpacing = 9;
			layout.horizontalSpacing = 20;
			
			GridData gridData;
			
			languageLabel.setText("Language");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			languageCombo.setLayoutData(gridData);
			languageCombo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					selectionFunctionResetControls();
					String language = languageCombo.getText();
					manageLanguage(language);
					((SRWizard) getWizard()).getParametersFilter().setLanguage(language);
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			sqlDialectLabel.setText("SQL Dialect");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			sqlDialectCombo.setLayoutData(gridData);
			sqlDialectCombo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setSqlDialect(sqlDialectCombo.getText());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			frameworkLabel.setText("Framework");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			frameworkCombo.setLayoutData(gridData);
			frameworkCombo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setFramework(frameworkCombo.getText());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			loadTypeLabel.setText("Load Type");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			loadTypeCombo.setLayoutData(gridData);
			loadTypeCombo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setLoadType(loadTypeCombo.getText());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			internetApplicationCheck.setText("Internet Application");
			internetApplicationCheck.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setIaApplication(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setIaApplication(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
//		consoleApplicationCheck.setText("Console Application");
//		gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalAlignment = SWT.FILL;
//		gridData.horizontalSpan = 4;
//		consoleApplicationCheck.setLayoutData(gridData);
//		consoleApplicationCheck.addSelectionListener(new  SelectionListener() {
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				Button button = (Button) arg0.getSource();
//				if(button.getSelection()) {
//					((SRWizard) getWizard()).getParametersFilter().setCaApplication(true);
//				} else {
//					((SRWizard) getWizard()).getParametersFilter().setCaApplication(false);
//				}
//			}
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {				
//			}
//		});
			
			targetBrowserCheck.setText("Target Browser");
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gridData.horizontalSpan = 4;
			targetBrowserCheck.setLayoutData(gridData);
			targetBrowserCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(targetBrowserCheck.getSelection()) {
						openBrowserShell();
					} else {
						ieVersion = null;
						chromeVersion = null;
						firefoxVersion = null;
						safariVersion = null;
						operaVersion = null;
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
//		folderCopyLabel.setText("Folder Copy");
//		
//		folderCopyButton.setText("Browse...");
//		gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalSpan = 4;
//		folderCopyButton.setLayoutData(gridData);
//		folderCopyButton.setEnabled(false);
//		folderCopyButton.addMouseListener(new MouseListener() {
//			@Override
//			public void mouseUp(MouseEvent arg0) {
//				DirectoryDialog dlg = new DirectoryDialog(shell);
//			    dlg.setText("Select Copybook Folder");
//			    String returnedPath = dlg.open();
//		        if (returnedPath!=null) {
//		        	((SRWizard) getWizard()).getParametersFilter().setFolderCopyPath(returnedPath);
////		        	setCopybookFolderPath(returnedPath);
//		        }
//			}
//			@Override
//			public void mouseDown(MouseEvent arg0) {
//
//			}
//			@Override
//			public void mouseDoubleClick(MouseEvent arg0) {
//
//			}
//		});
			
			maxVulnsLabel.setText("Max Vulnerabilities per Line");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			maxVulnsSpinner.setLayoutData(gridData);
			maxVulnsSpinner.setMinimum(1);
			maxVulnsSpinner.setMaximum(5);
			maxVulnsSpinner.setSelection(1);
			((SRWizard) getWizard()).getParametersFilter().setMaxVulns(maxVulnsSpinner.getText());
			maxVulnsSpinner.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setMaxVulns(maxVulnsSpinner.getText());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			ruleSetSecurityLabel.setText("Rule Set");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = 4;
			ruleSetSecurityCombo.setLayoutData(gridData);
			ruleSetSecurityCombo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setSecurityRuleSet(ruleSetSecurityCombo.getText());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
//		ruleSetDeadcodeLabel.setText("Rule Set");
//		
//		gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalAlignment = SWT.FILL;
//		gridData.horizontalSpan = 4;
//		ruleSetDeadcodeCombo.setLayoutData(gridData);
			
			exclusionListCheck.setText("Apply Exclusion List");
			exclusionListCheck.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gridData.horizontalSpan = 5;
			exclusionListCheck.setLayoutData(gridData);
			exclusionListCheck.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setExclusionList(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setExclusionList(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			sev1Check.setText("Sev. 1");
			sev1Check.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			sev1Check.setLayoutData(gridData);
			sev1Check.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setSev1(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setSev1(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			sev2Check.setText("Sev. 2");
			sev2Check.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			sev2Check.setLayoutData(gridData);
			sev2Check.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setSev2(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setSev2(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			sev3Check.setText("Sev. 3");
			sev3Check.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			sev3Check.setLayoutData(gridData);
			sev3Check.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setSev3(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setSev3(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			sev4Check.setText("Sev. 4");
			sev4Check.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			sev4Check.setLayoutData(gridData);
			sev4Check.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setSev4(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setSev4(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			sev5Check.setText("Sev. 5");
			sev5Check.setSelection(true);
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			sev5Check.setLayoutData(gridData);
			sev5Check.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setSev5(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setSev5(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			
			customLabel.setText("Custom Rules:");
			
			customButton.setText("Browse...");
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalSpan = 4;
			customButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					FileDialog fileDialog = new FileDialog(shell);
					fileDialog.setText("Select SR Incremental File from Previous Version");
					fileDialog.setFilterExtensions(new String[]{"*" + FileSystemConstant.SR_EXTENSION});
					String returnedPath = fileDialog.open();
					((SRWizard) getWizard()).getParametersFilter().setCustomRulesFilePath(returnedPath);
//				setCustomPath(returnedPath);
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
			});
			customButton.setLayoutData(gridData);
			
			browserShell.getShell().addShellListener(new ShellListener() {
				@Override
				public void shellIconified(ShellEvent arg0) {
					
				}
				
				@Override
				public void shellDeiconified(ShellEvent arg0) {
					
				}
				
				@Override
				public void shellDeactivated(ShellEvent arg0) {
					
				}
				
				@Override
				public void shellClosed(ShellEvent arg0) {
					srWizard.getParametersFilter().setIeVersion(ieVersion);
					srWizard.getParametersFilter().setChromeVersion(chromeVersion);
					srWizard.getParametersFilter().setFirefoxVersion(firefoxVersion);
					srWizard.getParametersFilter().setSafariVersion(safariVersion);
					srWizard.getParametersFilter().setOperaVersion(operaVersion);
				}
				
				@Override
				public void shellActivated(ShellEvent arg0) {
					
				}
			});
			
			trustedGroup.setText("Trusted Options");
			
			trustedApplicationCheck.setText("Trusted Application");
			trustedApplicationCheck.setSelection(true);
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		trustedApplicationCheck.setLayoutData(gridData);
			trustedApplicationCheck.setLocation(8, 20);
			trustedApplicationCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(trustedApplicationCheck.getSelection()) {
						publicFunctionCheck.setEnabled(true);
						dbQueriesCheck.setEnabled(true);
						socketCheck.setEnabled(true);
						environmentCheck.setEnabled(true);
						servletCheck.setEnabled(true);
//					openTrustedApplicationShell();
					} else {
						publicFunctionCheck.setSelection(false);
						dbQueriesCheck.setSelection(false);
						socketCheck.setSelection(false);
						environmentCheck.setSelection(false);
						servletCheck.setSelection(false);
						publicFunctionCheck.setEnabled(false);
						dbQueriesCheck.setEnabled(false);
						socketCheck.setEnabled(false);
						environmentCheck.setEnabled(false);
						servletCheck.setEnabled(false);
						((SRWizard) getWizard()).getParametersFilter().setCaApplication(false);
						((SRWizard) getWizard()).getParametersFilter().setDbQueries(false);
						((SRWizard) getWizard()).getParametersFilter().setSocket(false);
						((SRWizard) getWizard()).getParametersFilter().setEnvironmentVariables(false);
						((SRWizard) getWizard()).getParametersFilter().setServletRequest(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			trustedApplicationCheck.pack();
			
			publicFunctionCheck.setText("Public Functions");
			publicFunctionCheck.setSelection(true);
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		publicFunctionCheck.setLayoutData(gridData);
			publicFunctionCheck.setLocation(20, 40);
			publicFunctionCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(publicFunctionCheck.getSelection()) {
						((SRWizard) getWizard()).getParametersFilter().setCaApplication(true);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setCaApplication(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			publicFunctionCheck.pack();
			
			dbQueriesCheck.setText("DB Queries");
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		dbQueriesCheck.setLayoutData(gridData);
			dbQueriesCheck.setLocation(20, 60);
			dbQueriesCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(dbQueriesCheck.getSelection()) {
						( (SRWizard) getWizard()).getParametersFilter().setDbQueries(true);
					} else {
						( (SRWizard) getWizard()).getParametersFilter().setDbQueries(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			dbQueriesCheck.pack();
			
			socketCheck.setText("Socket");
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		socketCheck.setLayoutData(gridData);
			socketCheck.setLocation(20, 80);
			socketCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(socketCheck.getSelection()) {
						( (SRWizard) getWizard()).getParametersFilter().setSocket(true);
					} else {
						( (SRWizard) getWizard()).getParametersFilter().setSocket(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			socketCheck.pack();
			
			servletCheck.setText("Servlet/WS Request");
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		servletCheck.setLayoutData(gridData);
			servletCheck.setLocation(20, 100);
			servletCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(servletCheck.getSelection()) {
						( (SRWizard) getWizard()).getParametersFilter().setServletRequest(true);
					} else {
						( (SRWizard) getWizard()).getParametersFilter().setServletRequest(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			servletCheck.pack();
			
			environmentCheck.setText("Environment Variables");
//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
//		gridData.horizontalSpan = 5;
//		environmentCheck.setLayoutData(gridData);
			environmentCheck.setLocation(20, 120);
			environmentCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(environmentCheck.getSelection()) {
						( (SRWizard) getWizard()).getParametersFilter().setEnvironmentVariables(true);
					} else {
						( (SRWizard) getWizard()).getParametersFilter().setEnvironmentVariables(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			environmentCheck.pack();
			
			trustedGroup.pack();
			
			setFirstElements();
			
			setControl(container);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	
	private void inizializeControls(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		layout = new GridLayout();
		languageLabel = new Label(container, SWT.NULL);
		languageCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		sqlDialectLabel = new Label(container, SWT.NULL);
		sqlDialectCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		frameworkLabel = new Label(container, SWT.NULL);
		frameworkCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		loadTypeLabel = new Label(container, SWT.NULL);
		loadTypeCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		internetApplicationCheck = new Button(container, SWT.CHECK);
//		consoleApplicationCheck = new Button(container, SWT.CHECK);
		targetBrowserCheck = new Button(container, SWT.CHECK);
//		folderCopyLabel = new Label(container, SWT.NULL);
//		folderCopyButton = new Button(container, SWT.PUSH);
		maxVulnsLabel = new Label(container, SWT.NULL);
		maxVulnsSpinner = new Spinner(container, SWT.BORDER);
		ruleSetSecurityLabel = new Label(container, SWT.NULL);
		ruleSetSecurityCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
//		ruleSetDeadcodeLabel = new Label(container, SWT.NULL);
//		ruleSetDeadcodeCombo = new Combo(container, SWT.DROP_DOWN);
		exclusionListCheck = new Button(container, SWT.CHECK);
		sev1Check = new Button(container, SWT.CHECK);
		sev2Check = new Button(container, SWT.CHECK);
		sev3Check = new Button(container, SWT.CHECK);
		sev4Check = new Button(container, SWT.CHECK);
		sev5Check = new Button(container, SWT.CHECK);
		customLabel = new Label(container, SWT.NULL);
		customButton = new Button(container, SWT.PUSH);
		browserShell = new BrowserShell(getShell(), this);
		trustedGroup = new Group(container, SWT.SHADOW_ETCHED_IN);
		trustedApplicationCheck = new Button(trustedGroup, SWT.CHECK);
//		trustedApplicationShell = new TrustedApplicationShell(getShell(), this);
		publicFunctionCheck = new Button(trustedGroup, SWT.CHECK);
		dbQueriesCheck = new Button(trustedGroup, SWT.CHECK);
		socketCheck = new Button(trustedGroup, SWT.CHECK);
		servletCheck = new Button(trustedGroup, SWT.CHECK);
		environmentCheck = new Button(trustedGroup, SWT.CHECK);
	}
	
	private void inizializeLanguageCombo() {
		try {
//		languageCombo.add(WizardParametersConstant.LANGUAGE_ABAP);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_ANDROID);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_COBOL);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_CPP);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_CSHARP);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_IOS);
			languageCombo.add(WizardParametersConstant.LANGUAGE_JAVA);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_PHP);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_PYTHON);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_RUBY);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_SQL);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_VBNET);
//		languageCombo.add(WizardParametersConstant.LANGUAGE_VISUAL_BASIC);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void manageLanguage(String language) {
		try {
			if(WizardParametersConstant.LANGUAGE_ABAP.equals(language)) {
				abapSelection();
			} else if(WizardParametersConstant.LANGUAGE_ANDROID.equals(language)) {
				androidSelection();
			} else if(WizardParametersConstant.LANGUAGE_COBOL.equals(language)) {
				cobolSelection();
			} else if(WizardParametersConstant.LANGUAGE_CPP.equals(language)) {
				cppSelection();
			} else if(WizardParametersConstant.LANGUAGE_CSHARP.equals(language)) {
				csharpSelection();
			} else if(WizardParametersConstant.LANGUAGE_IOS.equals(language)) {
				iosSelection();
			} else if(WizardParametersConstant.LANGUAGE_JAVA.equals(language)) {
				javaSelection();
			} else if(WizardParametersConstant.LANGUAGE_PHP.equals(language)) {
				phpSelection();
			} else if(WizardParametersConstant.LANGUAGE_PYTHON.equals(language)) {
				pythonSelection();
			} else if(WizardParametersConstant.LANGUAGE_RUBY.equals(language)) {
				rubySelection();
			} else if(WizardParametersConstant.LANGUAGE_SQL.equals(language)) {
				sqlSelection();
			} else if(WizardParametersConstant.LANGUAGE_VBNET.equals(language)) {
				vbnetSelection();
			} else if(WizardParametersConstant.LANGUAGE_VISUAL_BASIC.equals(language)) {
				visualbasicSelection();
			}
			selectFirstComboElement();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void selectFirstComboElement() {
		try {
			javaSelection();
			languageCombo.select(0);
			sqlDialectCombo.select(0);
			frameworkCombo.select(0);
			loadTypeCombo.select(0);
			ruleSetSecurityCombo.select(1);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void setFirstElements() {
		try {
			((SRWizard) getWizard()).getParametersFilter().setLanguage(languageCombo.getText());
			((SRWizard) getWizard()).getParametersFilter().setSqlDialect(sqlDialectCombo.getText());
			((SRWizard) getWizard()).getParametersFilter().setFramework(frameworkCombo.getText());
			((SRWizard) getWizard()).getParametersFilter().setLoadType(loadTypeCombo.getText());
			((SRWizard) getWizard()).getParametersFilter().setSecurityRuleSet(ruleSetSecurityCombo.getText());
			((SRWizard) getWizard()).getParametersFilter().setExclusionList(exclusionListCheck.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setSev1(sev1Check.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setSev2(sev2Check.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setSev3(sev3Check.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setSev4(sev4Check.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setSev5(sev5Check.getSelection());
			((SRWizard) getWizard()).getParametersFilter().setCaApplication(publicFunctionCheck.getSelection());			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillSqlDialectCombo() {
		try {
			sqlDialectCombo.add(WizardParametersConstant.SQL_ANSI);
			sqlDialectCombo.add(WizardParametersConstant.SQL_FIREBIRD);
			sqlDialectCombo.add(WizardParametersConstant.SQL_IBM_DB2);
			sqlDialectCombo.add(WizardParametersConstant.SQL_IBM_INFORMIX);
			sqlDialectCombo.add(WizardParametersConstant.SQL_MYSQL);
			sqlDialectCombo.add(WizardParametersConstant.SQL_PLSQL);
			sqlDialectCombo.add(WizardParametersConstant.SQL_POSTGRE);
			sqlDialectCombo.add(WizardParametersConstant.SQL_SAP);
			sqlDialectCombo.add(WizardParametersConstant.SQL_SAS);
			sqlDialectCombo.add(WizardParametersConstant.SQL_SQLITE);
			sqlDialectCombo.add(WizardParametersConstant.SQL_TERADATA);
			sqlDialectCombo.add(WizardParametersConstant.SQL_TSQL);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillSqlFrameworkCombo() {
		try {
			frameworkCombo.add(WizardParametersConstant.SQL_ANSI);
			frameworkCombo.add(WizardParametersConstant.SQL_FIREBIRD);
			frameworkCombo.add(WizardParametersConstant.SQL_IBM_DB2);
			frameworkCombo.add(WizardParametersConstant.SQL_IBM_INFORMIX);
			frameworkCombo.add(WizardParametersConstant.SQL_MYSQL);
			frameworkCombo.add(WizardParametersConstant.SQL_PLSQL);
			frameworkCombo.add(WizardParametersConstant.SQL_POSTGRE);
			frameworkCombo.add(WizardParametersConstant.SQL_SAP);
			frameworkCombo.add(WizardParametersConstant.SQL_SAS);
			frameworkCombo.add(WizardParametersConstant.SQL_SQLITE);
			frameworkCombo.add(WizardParametersConstant.SQL_TERADATA);
			frameworkCombo.add(WizardParametersConstant.SQL_TSQL);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillFrameworkComboAndroid() {
		try {
			frameworkCombo.add(WizardParametersConstant.ANDROID_1);
			frameworkCombo.add(WizardParametersConstant.ANDROID_2);
			frameworkCombo.add(WizardParametersConstant.ANDROID_3);
			frameworkCombo.add(WizardParametersConstant.ANDROID_4);
			frameworkCombo.add(WizardParametersConstant.ANDROID_5);
			frameworkCombo.add(WizardParametersConstant.ANDROID_6);
			frameworkCombo.add(WizardParametersConstant.ANDROID_7);
			frameworkCombo.add(WizardParametersConstant.ANDROID_8);
			frameworkCombo.add(WizardParametersConstant.ANDROID_9);
			frameworkCombo.add(WizardParametersConstant.ANDROID_10);
			frameworkCombo.add(WizardParametersConstant.ANDROID_11);
			frameworkCombo.add(WizardParametersConstant.ANDROID_12);
			frameworkCombo.add(WizardParametersConstant.ANDROID_13);
			frameworkCombo.add(WizardParametersConstant.ANDROID_14);
			frameworkCombo.add(WizardParametersConstant.ANDROID_15);
			frameworkCombo.add(WizardParametersConstant.ANDROID_16);
			frameworkCombo.add(WizardParametersConstant.ANDROID_17);
			frameworkCombo.add(WizardParametersConstant.ANDROID_18);
			frameworkCombo.add(WizardParametersConstant.ANDROID_19);
			frameworkCombo.add(WizardParametersConstant.ANDROID_20);
			frameworkCombo.add(WizardParametersConstant.ANDROID_21);
			frameworkCombo.add(WizardParametersConstant.ANDROID_22);
			frameworkCombo.add(WizardParametersConstant.ANDROID_23);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillGenericRuleSet() {
		try {
			ruleSetSecurityCombo.add(WizardParametersConstant.RULE_SET_OWASP2010);
			ruleSetSecurityCombo.add(WizardParametersConstant.RULE_SET_OWASP2013);
			ruleSetSecurityCombo.add(WizardParametersConstant.RULE_SET_CWE);
		//		ruleSetDeadcodeCombo.add(WizardParametersConstant.RULE_SET_CWE);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillMobileRuleSet() {
		try {
			ruleSetSecurityCombo.add(WizardParametersConstant.RULE_SET_OWASP_MOBILE_2014);
		//		ruleSetDeadcodeCombo.add(WizardParametersConstant.RULE_SET_CWE);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void fillWindowsFramework() {
		try {
			frameworkCombo.add(WizardParametersConstant.WINDOWS_1);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_2);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_25);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_3);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_31);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_32);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_4);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_41);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_42);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_6);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_7);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_71);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_8);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_9);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_11);
			frameworkCombo.add(WizardParametersConstant.WINDOWS_12);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void selectionFunctionResetControls() {
		try {
			loadTypeCombo.removeAll();
			loadTypeCombo.setEnabled(false);
			sqlDialectCombo.removeAll();
			sqlDialectCombo.setEnabled(false);
			frameworkCombo.removeAll();
			frameworkCombo.setEnabled(false);
			targetBrowserCheck.setEnabled(true);
			internetApplicationCheck.setEnabled(true);
			ruleSetSecurityCombo.removeAll();
		//		ruleSetDeadcodeCombo.removeAll();
		//		folderCopyButton.setEnabled(false);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void disableIaTb() {
		try {
			targetBrowserCheck.setEnabled(true);
			internetApplicationCheck.setEnabled(true);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	public void completeCPPParameters() {
		try {
			frameworkCombo.removeAll();
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_SOLUTION);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_PROJECT);
			if(C_WIN_OPTION.equals(selection)) {
				frameworkCombo.setEnabled(true);
				fillWindowsFramework();
				frameworkCombo.select(0);
			}			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void abapSelection() {
		fillGenericRuleSet();
	}
	
	private void androidSelection() {
		try {
			disableIaTb();
			sqlDialectCombo.setEnabled(true);
			sqlDialectCombo.add(WizardParametersConstant.SQL_SQLITE);
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			frameworkCombo.setEnabled(true);
			fillFrameworkComboAndroid();
			fillMobileRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void cobolSelection() {
		try {
		//		folderCopyButton.setEnabled(true);
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void cppSelection() {
		openCPPShell();
	}
	
	private void csharpSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_SOLUTION);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_PROJECT);
			frameworkCombo.setEnabled(true);
			frameworkCombo.add(WizardParametersConstant.VB_1);
			frameworkCombo.add(WizardParametersConstant.VB_11);
			frameworkCombo.add(WizardParametersConstant.VB_2);
			frameworkCombo.add(WizardParametersConstant.VB_35);
			frameworkCombo.add(WizardParametersConstant.VB_4);
			frameworkCombo.add(WizardParametersConstant.VB_45);
			frameworkCombo.add(WizardParametersConstant.VB_5);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void iosSelection() {
		try {
			disableIaTb();
			sqlDialectCombo.setEnabled(true);
			sqlDialectCombo.add(WizardParametersConstant.SQL_SQLITE);
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillMobileRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void javaSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_CLASSPATH);
			frameworkCombo.setEnabled(true);
			frameworkCombo.add(WizardParametersConstant.JAVA_18);
			frameworkCombo.add(WizardParametersConstant.JAVA_17);
			frameworkCombo.add(WizardParametersConstant.JAVA_16);
			frameworkCombo.add(WizardParametersConstant.JAVA_15);
			frameworkCombo.add(WizardParametersConstant.JAVA_14);
			frameworkCombo.add(WizardParametersConstant.JAVA_13);
			frameworkCombo.add(WizardParametersConstant.JAVA_12);
			frameworkCombo.add(WizardParametersConstant.JAVA_1);
			frameworkCombo.add(WizardParametersConstant.JAVA_09);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void phpSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void pythonSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void rubySelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void sqlSelection() {
		try {
			frameworkCombo.setEnabled(true);
			fillSqlFrameworkCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void vbnetSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_SOLUTION);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_PROJECT);
			frameworkCombo.setEnabled(true);
			frameworkCombo.add(WizardParametersConstant.VB_1);
			frameworkCombo.add(WizardParametersConstant.VB_11);
			frameworkCombo.add(WizardParametersConstant.VB_2);
			frameworkCombo.add(WizardParametersConstant.VB_35);
			frameworkCombo.add(WizardParametersConstant.VB_4);
			frameworkCombo.add(WizardParametersConstant.VB_45);
			frameworkCombo.add(WizardParametersConstant.VB_5);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void visualbasicSelection() {
		try {
			sqlDialectCombo.setEnabled(true);
			fillSqlDialectCombo();
			loadTypeCombo.setEnabled(true);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_FOLDER);
			loadTypeCombo.add(WizardParametersConstant.LOAD_BY_PROJECT);
			frameworkCombo.setEnabled(true);
			frameworkCombo.add(WizardParametersConstant.VB_3);
			frameworkCombo.add(WizardParametersConstant.VB_4);
			frameworkCombo.add(WizardParametersConstant.VB_5);
			frameworkCombo.add(WizardParametersConstant.VB_6);
			fillGenericRuleSet();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void openBrowserShell() {
		try {
			browserShell = new BrowserShell(getShell(), this);
			browserShell.show();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void openCPPShell() {
		try {
			CPPShell cppShell = new CPPShell(getShell(), this);
			cppShell.show();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
}
