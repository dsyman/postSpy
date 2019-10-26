package it.poste.postspy.shell;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.WizardParametersConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.wizard.SrSecondWizardPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class BrowserShell {

	private SrSecondWizardPage secondWizardPage;
	
	private Shell shell;
	
	private String ieString;
	private String chromeString;
	private String firefoxString;
	private String safariString;
	private String operaString;
	
	private Label ieLabel;
	private Label chromeLabel;
	private Label firefoxLabel;
	private Label safariLabel;
	private Label operaLabel;
	private Combo ieCombo;
	private Combo chromeCombo;
	private Combo firefoxCombo;
	private Combo safariCombo;
	private Combo operaCombo;
	private Button saveButton;
	private Button cancelButton;
	
	private final String NONE = "NONE";
	
	public BrowserShell(Shell shell, SrSecondWizardPage secondWizardPage) {
		this.shell = new Shell(shell);
		this.secondWizardPage = secondWizardPage;
	}
	
	public Shell getShell() {
		return shell;
	}

	public String getIeString() {
		return ieString;
	}

	public String getChromeString() {
		return chromeString;
	}

	public String getFirefoxString() {
		return firefoxString;
	}

	public String getSafariString() {
		return safariString;
	}
	
	public String getOperaString() {
		return operaString;
	}

	public void show() {
		try {
			secondWizardPage.getShell().setEnabled(false);
			shell.setSize(800, 200);
			shell.setText("Select Target Browser");
			shell.addShellListener(new ShellListener() {
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
					secondWizardPage.getShell().setEnabled(true);
				}			
				@Override
				public void shellActivated(ShellEvent arg0) {
					
				}
			});
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 5;
			gridLayout.verticalSpacing = 9;
			gridLayout.horizontalSpacing = 40;
			shell.setLayout(gridLayout);
			inizializeComponent();
			selectFirstComboElement();
			
			ieLabel.setText("Internet Explorer");
			
			chromeLabel.setText("Chrome");
			
			firefoxLabel.setText("Firefox");
			
			safariLabel.setText("Safari");
			
			operaLabel.setText("Opera");
			
			saveButton.setText("Save");
			saveButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					fillBrowserList();
					closeShell();
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalSpan = 5;
			saveButton.setLayoutData(gridData);
			
			cancelButton.setText("Cancel");
			cancelButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					closeShell();
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			
			shell.open();
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void inizializeComponent() {
		ieLabel = new Label(shell, SWT.NULL);
		chromeLabel = new Label(shell, SWT.NULL);
		firefoxLabel = new Label(shell, SWT.NULL);
		safariLabel = new Label(shell, SWT.NULL);
		operaLabel = new Label(shell, SWT.NULL);
		ieCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		chromeCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		firefoxCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		safariCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		operaCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		saveButton = new Button(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		cancelButton = new Button(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		fillCombo();
	}
	
	private void fillCombo() {
		ieCombo.add(WizardParametersConstant.BROWSER_NONE_VERSION);
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.BROWSER_IE_ALL_VERSION));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_8));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_7));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_6));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_5));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_4));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_3));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_2));
		ieCombo.add(getBrowserTextFromConstant(WizardParametersConstant.IE_1));
		
		chromeCombo.add(WizardParametersConstant.BROWSER_NONE_VERSION);
		chromeCombo.add(getBrowserTextFromConstant(WizardParametersConstant.BROWSER_CHROME_ALL_VERSION));
		chromeCombo.add(getBrowserTextFromConstant(WizardParametersConstant.CHROME_3));
		chromeCombo.add(getBrowserTextFromConstant(WizardParametersConstant.CHROME_2));
		chromeCombo.add(getBrowserTextFromConstant(WizardParametersConstant.CHROME_1));
		
		firefoxCombo.add(WizardParametersConstant.BROWSER_NONE_VERSION);
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.BROWSER_FIREFOX_ALL_VERSION));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_10));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_9));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_8));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_7));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_6));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_5));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_4));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_3));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_2));
		firefoxCombo.add(getBrowserTextFromConstant(WizardParametersConstant.FIREFOX_1));
		
		safariCombo.add(WizardParametersConstant.BROWSER_NONE_VERSION);
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_10));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_9));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_8));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_7));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_6));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_5));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_4));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_3));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_2));
		safariCombo.add(getBrowserTextFromConstant(WizardParametersConstant.SAFARI_1));
		
		operaCombo.add(WizardParametersConstant.BROWSER_NONE_VERSION);
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.BROWSER_OPERA_ALL_VERSION));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_12));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_11));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_10));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_9));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_8));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_7));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_6));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_5));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_4));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_3));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_2));
		operaCombo.add(getBrowserTextFromConstant(WizardParametersConstant.OPERA_1));
	}
	
	private void selectFirstComboElement() {
		for(Control control:shell.getChildren()) {
			if(control instanceof Combo) {
				((Combo) control).select(0);
			}
		}
	}
	
	private void fillBrowserList() {
		ieString = ieCombo.getText();
		chromeString = chromeCombo.getText();
		firefoxString = firefoxCombo.getText();
		safariString = safariCombo.getText();
		operaString = operaCombo.getText();
		if(!NONE.equals(ieString)) {
//			((SRWizard) secondWizardPage.getWizard()).getParametersFilter().setIeVersion(ieString);
			secondWizardPage.setIeVersion(ieString);
		}
		if(!NONE.equals(chromeString)) {
//			((SRWizard) secondWizardPage.getWizard()).getParametersFilter().setChromeVersion(chromeString);
			secondWizardPage.setChromeVersion(chromeString);
		}
		if(!NONE.equals(firefoxString)) {
//			((SRWizard) secondWizardPage.getWizard()).getParametersFilter().setFirefoxVersion(firefoxString);
			secondWizardPage.setFirefoxVersion(firefoxString);
		}
		if(!NONE.equals(safariString)) {
//			((SRWizard) secondWizardPage.getWizard()).getParametersFilter().setSafariVersion(safariString);
			secondWizardPage.setSafariVersion(safariString);
		}
		if(!NONE.equals(operaString)) {
//			((SRWizard) secondWizardPage.getWizard()).getParametersFilter().setOperaVersion(operaString);
			secondWizardPage.setOperaVersion(operaString);
		}
	}
	
	private void closeShell() {
		shell.close();
	}
	
	private String getBrowserTextFromConstant(String browserConstant) {
		return browserConstant.split("/")[0];
	}
	
}
