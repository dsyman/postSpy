package it.poste.postspy.wizard;

import it.poste.postspy.constant.FileSystemConstant;
import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.shell.RemoteSettingsShell;
import it.poste.postspy.utility.LogUtility;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SrFirstWizardPage extends WizardPage {
	
	private String projectPath, projectName;
	private Shell shell;
	
	private Composite container;
	private GridLayout layout;
	private Label projectPathLabel;
	private Text projectPatText;
	private Label projectNameLabel;
	private Text projectNameText;
	private Label projectVersionLabel;
	private Text projectVersionText;
	private Label auditorLabel;
	private Text auditorText;
	private Label auditDateLabel;
	private DateTime auditDateTime;
	private Button qualityCheck;
	private Button securityCheck;
	private Button baselineCheck;
	private Button incrementalButton;
	private Label incrementalLabel;
	private Button remoteScanCheck;
	private Label previousVersionLabel;
	private Text previousVersionText;
	
	private String incrementalFilePath;
	
	RemoteSettingsShell remoteSettingsShell;
	
	public Text getProjectPatText() {
		return projectPatText;
	}
	public Text getProjectNameText() {
		return projectNameText;
	}
	public Text getProjectVersionText() {
		return projectVersionText;
	}
	public Text getAuditorText() {
		return auditorText;
	}
	public DateTime getAuditDateTime() {
		return auditDateTime;
	}
	public Button getQualityCheck() {
		return qualityCheck;
	}
	public Button getSecurityCheck() {
		return securityCheck;
	}
	public Button getBaselineCheck() {
		return baselineCheck;
	}
	public Button getIncrementalButton() {
		return incrementalButton;
	}

	public String getIncrementalFilePath() {
		return incrementalFilePath;
	}
	
	public SrFirstWizardPage(String projectPath, String projectName, Shell shell) {
		super("wizardPage");
		this.shell = shell;
		setTitle("Security Reviewer Scan Options 1/2");
		this.projectPath = projectPath;
		this.projectName = projectName;
		
	}

	public void createControl(Composite parent) {
		try {
			getShell().setSize(750, 670);
			getShell().layout(true, true);
			Monitor primary = getShell().getDisplay().getPrimaryMonitor();
			Rectangle bounds = primary.getBounds();
			Rectangle rect = getShell().getBounds();
			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 2;
			getShell().setLocation(x, y);
			inizializeControls(parent);
			
			container.setLayout(layout);
			layout.numColumns = 2;
			layout.verticalSpacing = 9;
			layout.horizontalSpacing = 20;
			
			GridData gridData;
			
			projectPathLabel.setText("Project Path:");
			
			projectPatText.setText(projectPath);
			projectPatText.setEnabled(false);
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.widthHint = 550;
			gridData.horizontalAlignment = SWT.FILL;
			projectPatText.setLayoutData(gridData);
//		projectPatText.addModifyListener(new ModifyListener() {
//			@Override
//			public void modifyText(ModifyEvent arg0) {
//				((SRWizard) getWizard()).getParametersFilter().setProjectPath(projectPatText.getText());
//			}
//		});
//		
			projectNameLabel.setText("Project Name:");
			
			projectNameText.setText(projectName.toUpperCase());
			projectNameText.setEnabled(false);
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			projectNameText.setLayoutData(gridData);
//		projectNameText.addModifyListener(new ModifyListener() {
//			@Override
//			public void modifyText(ModifyEvent arg0) {
//				((SRWizard) getWizard()).getParametersFilter().setProjectName(projectNameText.getText());
//			}
//		});
			
			projectVersionLabel.setText("Project Version:");
			
			projectVersionText.setEnabled(true);
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			projectVersionText.setLayoutData(gridData);
			projectVersionText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setProjectVersion(projectVersionText.getText().trim());
				}
			});
			projectVersionText.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					projectVersionText.setText(projectVersionText.getText().toUpperCase());
					projectVersionText.append("");
				}
				@Override
				public void keyPressed(KeyEvent arg0) {
					
				}
			});
			
			auditorLabel.setText("Auditor:");
			
			auditorText.setEnabled(true);
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			auditorText.setLayoutData(gridData);
			auditorText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setAuditor(auditorText.getText().trim());
				}
			});
			auditorText.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					auditorText.setText(auditorText.getText().toUpperCase());
					auditorText.append("");
				}
				@Override
				public void keyPressed(KeyEvent arg0) {
					
				}
			});
			
			auditDateLabel.setText("AuditDate:");
			
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalAlignment = SWT.FILL;
			auditDateTime.setLayoutData(gridData);
			((SRWizard) getWizard()).getParametersFilter().setAuditDate(getDateString(auditDateTime));
			auditDateTime.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setAuditDate(getDateString(auditDateTime));
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			
			qualityCheck.setText("Quality Analysis");
			qualityCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(qualityCheck.getSelection()) {
						securityCheck.setSelection(false);
						((SRWizard) getWizard()).getParametersFilter().setQualityAnalysis(true);
						((SRWizard) getWizard()).getParametersFilter().setStaticAnalysis(false);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setQualityAnalysis(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			securityCheck.setText("Security\\DeadCode Analysis");
			securityCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(securityCheck.getSelection()) {
						qualityCheck.setSelection(false);
						((SRWizard) getWizard()).getParametersFilter().setStaticAnalysis(true);
						((SRWizard) getWizard()).getParametersFilter().setQualityAnalysis(false);
					} else {
						((SRWizard) getWizard()).getParametersFilter().setStaticAnalysis(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			baselineCheck.setText("Baseline");
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gridData.horizontalSpan = 2;
			baselineCheck.addSelectionListener(new  SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button button = (Button) arg0.getSource();
					if(button.getSelection()) {
						setEnableToIncrementalButton(false);
						((SRWizard) getWizard()).getParametersFilter().setBaseline(true);
					} else {
						setEnableToIncrementalButton(true);
						((SRWizard) getWizard()).getParametersFilter().setBaseline(false);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {				
				}
			});
			baselineCheck.setLayoutData(gridData);
			
			incrementalLabel.setText("Incremental Analysis:");
			
			incrementalButton.setText("Browse...");
			incrementalButton.setEnabled(true);
			incrementalButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					FileDialog fileDialog = new FileDialog(shell);
					fileDialog.setText("Select SR Incremental File from Previous Version");
					fileDialog.setFilterExtensions(new String[]{"*" + FileSystemConstant.SR_EXTENSION});
					String returnedPath = fileDialog.open();
					((SRWizard) getWizard()).getParametersFilter().setIncrementalFilePath(returnedPath);
//				setIncrementalFilePath(returnedPath);
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
			});
			
			remoteScanCheck.setText("RemoteScan");
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gridData.horizontalSpan = 2;
			remoteScanCheck.setLayoutData(gridData);
			remoteScanCheck.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(remoteScanCheck.getSelection()) {
						previousVersionText.setEnabled(true);
						incrementalButton.setEnabled(false);
						((SRWizard) getWizard()).getParametersFilter().setRemoteScan(true);
						openRemoteSettingsShell();
					} else {
						((SRWizard) getWizard()).getParametersFilter().setRemoteScan(false);
						previousVersionText.setEnabled(false);
						incrementalButton.setEnabled(baselineCheck.getSelection()?false:true);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			previousVersionLabel.setText("Previous Version:");
			
			previousVersionText.setEnabled(false);
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalAlignment = SWT.FILL;
			previousVersionText.setLayoutData(gridData);
			previousVersionText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					((SRWizard) getWizard()).getParametersFilter().setPreviousVersion(previousVersionText.getText().trim());
				}
			});
			previousVersionText.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					previousVersionText.setText(previousVersionText.getText().toUpperCase());
					previousVersionText.append("");
				}
				@Override
				public void keyPressed(KeyEvent arg0) {
					
				}
			});
			
			setControl(container);			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void inizializeControls(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		layout = new GridLayout();
		projectPathLabel = new Label(container, SWT.NULL);
		projectPatText = new Text(container, SWT.BORDER | SWT.SINGLE);
		projectNameLabel = new Label(container, SWT.NULL);
		projectNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		projectVersionLabel = new Label(container, SWT.NULL);
		projectVersionText = new Text(container, SWT.BORDER | SWT.SINGLE);
		auditorLabel = new Label(container, SWT.NULL);
		auditorText = new Text(container, SWT.BORDER | SWT.SINGLE);
		auditDateLabel = new Label(container, SWT.NULL);
		auditDateTime = new DateTime(container, SWT.CALENDAR);
		qualityCheck = new Button(container, SWT.CHECK);
		securityCheck = new Button(container, SWT.CHECK);
		baselineCheck = new Button(container, SWT.CHECK);
		incrementalLabel = new Label(container, SWT.NULL);
		incrementalButton = new Button(container, SWT.PUSH);
		remoteScanCheck = new Button(container, SWT.CHECK);
		previousVersionLabel = new Label(container, SWT.NULL);
		previousVersionText = new Text(container, SWT.BORDER | SWT.SINGLE);
	}
	
	private void setEnableToIncrementalButton(boolean enable) {
		incrementalButton.setEnabled(enable);
	}
	
	private String getDateString(DateTime dateTime) {
		String date = "";
		if(dateTime!=null) {
			date += dateTime.getMonth() + 1 + "/" + dateTime.getDay() + "/" + dateTime.getYear();
		}
		return date;
	}
	
	private void openRemoteSettingsShell() {
		try {
			remoteSettingsShell = new RemoteSettingsShell(getShell());
			remoteSettingsShell.show();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
}